package com.mybatis.load.xml;

import com.mybatis.binding.SqlCommandType;
import com.mybatis.load.document.XNode;
import com.mybatis.mapping.MappedStatement;
import com.mybatis.mapping.SqlSource;
import com.mybatis.session.Configuration;

import java.util.Locale;

/**
 * mapper xml中的mappedStatement解析
 */
public class XmlStatementBuilder {

    private final Configuration configuration;
    private final XNode context;
    private final String namespace;

    public XmlStatementBuilder(Configuration configuration, XNode context, String namespace) {
        this.configuration = configuration;
        this.context = context;
        this.namespace = namespace;
    }

    /**
     * 解析方法
     */
    public void parse(){
        String id = context.getStringAttribute("id",null);
        String nodeName = context.getNode().getNodeName();
        SqlCommandType sqlCommandType = SqlCommandType.UNKNOWN;
        if("select".equals(nodeName.toLowerCase(Locale.ENGLISH))){
            sqlCommandType = SqlCommandType.SELECT;
        }
        String parameterType = context.getStringAttribute("parameterType", null);
        //这里用null则选用默认的XmlLanguageDriver
        LanguageDriver languageDriver = configuration.getLanguageDriver(null);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, context, configuration.getTypeAliasRegistry().resolveAlias(parameterType));
        String msId = namespace + "." +id;
        MappedStatement mappedStatement = new MappedStatement(msId, sqlCommandType, sqlSource, configuration);
        configuration.addMappedStatement(mappedStatement);
    }
}
