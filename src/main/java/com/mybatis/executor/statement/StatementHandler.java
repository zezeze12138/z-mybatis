package com.mybatis.executor.statement;

import com.mybatis.session.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface StatementHandler {

    Statement prepare(Connection connection, Integer transactionTimeout);

    <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException;

    void parameterize(Statement statement);
}
