package com.mybatis.executor;

import com.mybatis.executor.statement.StatementHandler;
import com.mybatis.mapping.BoundSql;
import com.mybatis.mapping.MappedStatement;
import com.mybatis.session.Configuration;
import com.mybatis.session.ResultHandler;
import com.mybatis.session.RowBounds;
import com.mybatis.transaction.Transaction;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class SimpleExecutor extends BaseExecutor {


    public SimpleExecutor(Configuration configuration, Transaction transaction) {
        super(configuration, transaction);
    }

    @Override
    protected <E> List<E> doQuery(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        Statement stmt = null;
        try{
            Configuration configuration = ms.getConfiguration();
            StatementHandler handler = configuration.newStatementHandler(wrapper, ms, parameter, rowBounds, resultHandler, boundSql);
            stmt = prepareStatement(handler);
            return handler.query(stmt, resultHandler);
        }finally {
            //关闭statement
            closeStatement(stmt);
        }
    }

    private Statement prepareStatement(StatementHandler handler) {
        Statement statement;
        Connection connection = getConnection();
        statement = handler.prepare(connection, transaction.getTimeout());
        handler.parameterize(statement);
        return statement;
    }
}
