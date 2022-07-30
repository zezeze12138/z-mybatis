package com.mybatis.session;

import com.mybatis.executor.Executor;

/**
 * 默认的SqlSession接口实现类
 */
public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;
    private final Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    public <T> T selectList(String statement) {
        return null;
    }

    /**
     * 获取mapper
     * @param type 接口mapper类
     * @return 返回mapper类实例
     */
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }


}
