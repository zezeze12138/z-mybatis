package com.mybatis.executor.statement;

import com.mybatis.executor.Executor;
import com.mybatis.executor.parameter.ParameterHandler;
import com.mybatis.executor.resultset.ResultSetHandler;
import com.mybatis.mapping.BoundSql;
import com.mybatis.mapping.MappedStatement;
import com.mybatis.session.Configuration;
import com.mybatis.session.ResultHandler;
import com.mybatis.session.RowBounds;
import com.mybatis.type.TypeHandlerRegistry;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 基础的Statement处理器
 */
public abstract class BaseStatementHandler implements StatementHandler {

    protected final Configuration configuration;
    protected final TypeHandlerRegistry typeHandlerRegistry;
    protected final ResultSetHandler resultSetHandler;
    protected final ParameterHandler parameterHandler;

    protected final Executor executor;
    protected final MappedStatement mappedStatement;
    protected final RowBounds rowBounds;

    protected BoundSql boundSql;

    public BaseStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObjedt, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        this.configuration = mappedStatement.getConfiguration();
        this.executor = executor;
        this.mappedStatement = mappedStatement;
        this.rowBounds = rowBounds;
        this.typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        this.parameterHandler = configuration.newParameterHandler(mappedStatement, parameterObjedt, boundSql);
        this.resultSetHandler = configuration.newResultSetHandler(executor, mappedStatement, rowBounds, parameterHandler, resultHandler, boundSql);
        this.boundSql = boundSql;
    }

    @Override
    public Statement prepare(Connection connection, Integer transactionTimeout) {
        Statement statement = null;
        try{
            statement = instantiateStatement(connection);
            // TODO: 2022/8/8  这里有个设置超时时间的操作，暂时不实现
            return statement;
        }catch (Exception e){
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException sql) {

            }
            throw new RuntimeException("初始化statment异常");
        }
    }

    protected abstract Statement instantiateStatement(Connection connection) throws SQLException;

}
