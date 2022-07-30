package com.mybatis.load.xml;

import com.mybatis.session.Configuration;

public abstract class BaseBuilder {

    protected final Configuration configuration;


    protected BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
    }
}
