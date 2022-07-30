package com.mybatis.session;

/**
 *
 */
public interface SqlSession {

    /**
     * 查询列表
     * @param statement
     * @param <T>
     * @return
     */
    <T> T selectList(String statement);

    /**
     * 获取Mapper
     * @param type
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> type);
}
