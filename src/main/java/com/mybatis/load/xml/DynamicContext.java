package com.mybatis.load.xml;

import com.mybatis.session.Configuration;

import java.util.StringJoiner;

public class DynamicContext {

    private final StringJoiner sqlBuilder = new StringJoiner(" ");

    public DynamicContext(Configuration configuration, Object parameterObject) {
    }

    public void appendSql(String sql){
        sqlBuilder.add(sql);
    }

    public String getSql(){
        return sqlBuilder.toString().trim();
    }
}
