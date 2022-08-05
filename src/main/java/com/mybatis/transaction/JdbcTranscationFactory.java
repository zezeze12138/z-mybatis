package com.mybatis.transaction;

import java.sql.Connection;

/**
 * Jdbc事务工厂实现
 */
public class JdbcTranscationFactory implements TransactionFactory {
    @Override
    public Transaction newTransaction(Connection conn) {
        return new JdbcTranscation(conn);
    }
}
