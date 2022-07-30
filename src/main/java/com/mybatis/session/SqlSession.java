package com.mybatis.session;

/**
 *
 */
public interface SqlSession {

    /**
     * 查询一条
     * @param statement
     * @param <T>
     * @return
     */
    <T> T selectOne(String statement);

    /**
     * 获取Mapper
     * @param type
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> type);
}
