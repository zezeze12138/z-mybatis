package com.mybatis.transaction;

import java.sql.Connection;

/**
 * 事务接口
 */
public interface Transaction {

    /**
     * 获取连接
     * @return
     */
    Connection getConnection();

    /**
     * 提交
     */
    void commit();

    /**
     * 回滚
     */
    void rollback();

    /**
     * 关闭连接
     */
    void close();

    /**
     * 超时
     * @return
     */
    Integer getTimeout();
}
