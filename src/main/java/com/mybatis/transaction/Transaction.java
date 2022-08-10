package com.mybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务接口
 */
public interface Transaction {

    /**
     * 获取连接
     * @return
     */
    Connection getConnection() throws SQLException;

    /**
     * 提交
     */
    void commit() throws SQLException;

    /**
     * 回滚
     */
    void rollback() throws SQLException;

    /**
     * 关闭连接
     */
    void close() throws SQLException;

    /**
     * 超时
     * @return
     */
    Integer getTimeout();
}
