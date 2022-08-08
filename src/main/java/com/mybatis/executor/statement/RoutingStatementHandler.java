package com.mybatis.executor.statement;

import com.mybatis.executor.Executor;
import com.mybatis.mapping.BoundSql;
import com.mybatis.mapping.MappedStatement;
import com.mybatis.session.ResultHandler;
import com.mybatis.session.RowBounds;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class RoutingStatementHandler implements StatementHandler {

    private final StatementHandler delegate;

    public RoutingStatementHandler(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        switch (ms.getStatementType()){
            case STATEMENT:
                delegate = new SimpleStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql);
                break;
            default:
                throw new RuntimeException("未找到匹配的StatementType的StatementHandler实现");
        }
    }

    @Override
    public Statement prepare(Connection connection, Integer transactionTimeout) {
        return delegate.prepare(connection, transactionTimeout);
    }

    @Override
    public <E> List<E> query(Statement statement, ResultHandler resultHandler) {
        return delegate.query(statement, resultHandler);
    }

    @Override
    public void parameterize(Statement statement) {
        delegate.parameterize(statement);
    }
}
