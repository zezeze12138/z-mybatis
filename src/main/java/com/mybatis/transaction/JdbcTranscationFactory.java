package com.mybatis.transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/**
 * Jdbc事务工厂实现
 */
public class JdbcTranscationFactory implements TransactionFactory {
    @Override
    public void setProperties(Properties properties) {
        //这里暂时不用做什么设置动作
    }

    @Override
    public Transaction newTransaction(Connection conn) {
        return new JdbcTranscation(conn);
    }

    @Override
    public Transaction newTransaction(DataSource dataSource) {
        return new JdbcTranscation(dataSource);
    }
}
