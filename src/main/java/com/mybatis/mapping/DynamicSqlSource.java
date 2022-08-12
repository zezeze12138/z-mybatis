package com.mybatis.mapping;

import com.mybatis.load.document.SqlNode;
import com.mybatis.session.Configuration;

public class DynamicSqlSource implements SqlSource {

    private final Configuration configuration;
    private final SqlNode sqlNode;

    public DynamicSqlSource(Configuration configuration, SqlNode sqlNode) {
        this.configuration = configuration;
        this.sqlNode = sqlNode;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return null;
    }
}
