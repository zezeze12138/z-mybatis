package com.mybatis.mapping;

import com.mybatis.session.Configuration;

import java.util.List;

/**
 * 静态sql资源
 */
public class StaticSqlSource implements SqlSource{

    private final Configuration configuration;
    private final String sql;
    private final List<ParameterMapping> parameterMappingList;

    public StaticSqlSource(Configuration configuration, String sql, List<ParameterMapping> parameterMappingList) {
        this.configuration = configuration;
        this.sql = sql;
        this.parameterMappingList = parameterMappingList;
    }


    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return new BoundSql(sql);
    }
}
