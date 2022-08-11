package com.mybatis.load.xml;

import com.mybatis.load.document.XNode;
import com.mybatis.mapping.SqlSource;
import com.mybatis.session.Configuration;

public class XmlLanguageDriver implements LanguageDriver{

    @Override
    public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> paramterType) {
        XmlScriptBuilder xmlScriptBuilder = new XmlScriptBuilder(configuration, script, paramterType);
        return xmlScriptBuilder.parse();
    }

}
