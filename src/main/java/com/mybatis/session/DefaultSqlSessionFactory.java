package com.mybatis.session;

/**
 * 默认的SqlSessionFactory工厂
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    /**
     * 构造方法，传入配置信息
     * @param configuration
     */
    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSession() {
        return null;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
