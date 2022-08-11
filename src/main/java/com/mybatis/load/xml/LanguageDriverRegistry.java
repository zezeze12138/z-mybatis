package com.mybatis.load.xml;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 语言驱动注册类
 */
public class LanguageDriverRegistry {

    private final Map<Class<? extends LanguageDriver>, LanguageDriver> languageDriverMap = new HashMap<>();
    private Class<? extends LanguageDriver> defaultDriverClass;

    public void register(Class<? extends LanguageDriver> languageDriverClass){
        if(languageDriverClass == null){
            throw new RuntimeException("语言驱动类不能为空");
        }
        try {
            languageDriverMap.put(languageDriverClass, languageDriverClass.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Class<? extends LanguageDriver> getDefaultDriverClass(){
        return defaultDriverClass;
    }

    /**
     * 获取默认的语言驱动实现
     * @return
     */
    public LanguageDriver getDefaultLanguageDriver(){
        return languageDriverMap.get(defaultDriverClass);
    }

    public LanguageDriver getLanguageDriver(Class<? extends LanguageDriver> languageDriverClass){
        return languageDriverMap.get(languageDriverClass);
    }

    public void setDefaultDriverClass(Class<? extends LanguageDriver> defaultDriverClass){
        register(defaultDriverClass);
        this.defaultDriverClass = defaultDriverClass;
    }
}
