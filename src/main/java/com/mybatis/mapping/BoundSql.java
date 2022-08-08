package com.mybatis.mapping;

/**
 * 绑定的sql
 */
public class BoundSql {

    private final String sql;

    public BoundSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
