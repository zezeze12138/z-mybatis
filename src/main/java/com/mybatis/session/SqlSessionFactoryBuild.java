package com.mybatis.session;

import com.mybatis.load.xml.XmlConfigBuilder;

import java.io.InputStream;

public class SqlSessionFactoryBuild {

    /**
     * 创建SqlSessionFactory
     * @param config 配置信息
     * @return
     */
    public SqlSessionFactory build(Configuration config){
        return new DefaultSqlSessionFactory(config);
    }

}
