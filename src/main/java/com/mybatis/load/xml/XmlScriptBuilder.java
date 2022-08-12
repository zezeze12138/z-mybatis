package com.mybatis.load.xml;

import com.mybatis.load.document.XNode;
import com.mybatis.mapping.SqlSource;
import com.mybatis.session.Configuration;

/**
 * sqlSource解析
 */
public class XmlScriptBuilder {

    private final Configuration configuration;
    private final XNode script;
    private final Class<?> parameterType;

    public XmlScriptBuilder(Configuration configuration, XNode script, Class<?> parameterType) {
        this.configuration = configuration;
        this.script = script;
        this.parameterType = parameterType;
    }

    /**
     * 解析方法
     */
    public SqlSource parse(){

        return null;
    }
}
