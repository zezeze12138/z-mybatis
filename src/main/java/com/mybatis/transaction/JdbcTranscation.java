package com.mybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * jdbc事务
 */
public class JdbcTranscation implements Transaction {

    private Connection connection;


    public JdbcTranscation(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void commit() throws SQLException {
        connection.commit();
    }

    @Override
    public void rollback() throws SQLException {
        connection.rollback();
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }

    @Override
    public Integer getTimeout() {
        return null;
    }
}
