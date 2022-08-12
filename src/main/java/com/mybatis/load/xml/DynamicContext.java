package com.mybatis.load.xml;

import com.mybatis.session.Configuration;

import java.util.StringJoiner;

public class DynamicContext {

    private final StringJoiner sqlBuilder = new StringJoiner(" ");

    public DynamicContext(Configuration configuration, Object parameterObject) {
    }
}
