package com.mybatis.load.xml;

import java.util.Properties;

public class PropertyParser {

    private static final String KEY_PREFIX = "com.mybatis.load.xml.PropertyParser.";
    private static final String KEY_ENABLE_DEFAULT_VALUE = KEY_PREFIX + "enable-default-value";
    private static final String KEY_DEFAULT_VALUE_SEPARATOR = KEY_PREFIX + "enable-default-separator";
    private static final String ENABLE_DEFAULT_VALUE = "false";
    private static final String DEFAULT_VALUE_SEPARATOR = ":";

    public static String parse(String context, Properties variables){
        //完成全局${}内容的全局替换
        VariableTokenHandler handler = new VariableTokenHandler(variables);
        GenericToeknParser parser = new GenericToeknParser("${","}", handler);
        return parser.parse(context);
    }


    private static class VariableTokenHandler implements TokenHandler{

        private final Properties variables;
        private final boolean enableDefaultValue;
        private final String defaultValueSeparator;

        /**
         * 配合GenericTokenParaser完成Mybatis的占位符${}格式的处理。
         * @param variables
         */
        public VariableTokenHandler(Properties variables) {
            this.variables = variables;
            //入参variables里面有配置的话就用配置的，没有的话就用默认的。
            this.enableDefaultValue = Boolean.parseBoolean(getPropertyValue(KEY_ENABLE_DEFAULT_VALUE, ENABLE_DEFAULT_VALUE));
            this.defaultValueSeparator = getPropertyValue(KEY_DEFAULT_VALUE_SEPARATOR, DEFAULT_VALUE_SEPARATOR);
        }

        private String getPropertyValue(String key, String defaultValue){
            return (variables == null) ? defaultValue : variables.getProperty(key, defaultValue);
        }


        @Override
        public String handlerToken(String content) {
            if(variables != null){
                String key = content;
                if(enableDefaultValue){
                    final int separatorIndex = content.indexOf(defaultValueSeparator);
                    String defaultValue = null;
                    if(separatorIndex >= 0){
                        key = content.substring(0, separatorIndex);
                        defaultValue = content.substring(separatorIndex + defaultValueSeparator.length());
                    }
                    if(defaultValue != null){
                        return variables.getProperty(key, defaultValue);
                    }
                }
                if(variables.contains(key)){
                    return variables.getProperty(key);
                }
            }
            return "${"+content+"}";
        }
    }
}
