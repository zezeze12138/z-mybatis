package com.mybatis.session;

import java.util.List;

/**
 *
 */
public interface SqlSession {

    /**
     * 查询列表
     * @param statement
     * @param parameter 查询参数
     * @return
     */
    <E> List<E> selectList(String statement, Object parameter);

    /**
     * 获取Mapper
     * @param type
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> type);


    /**
     * 获取配置信息
     * @return
     */
    Configuration getConfiguration();
}
