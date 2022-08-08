package com.mybatis.executor.statement;

import com.mybatis.executor.Executor;
import com.mybatis.mapping.BoundSql;
import com.mybatis.mapping.MappedStatement;
import com.mybatis.session.ResultHandler;
import com.mybatis.session.RowBounds;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class SimpleStatementHandler implements StatementHandler {
    public SimpleStatementHandler(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {


    }

    @Override
    public Statement prepare(Connection connection, Integer transactionTimeout) {
        return null;
    }

    @Override
    public <E> List<E> query(Statement statement, ResultHandler resultHandler) {
        return null;
    }

    @Override
    public void parameterize(Statement statement) {

    }
}
