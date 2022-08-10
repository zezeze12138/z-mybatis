package com.mybatis.executor.resultset;

import com.mybatis.executor.Executor;
import com.mybatis.executor.parameter.ParameterHandler;
import com.mybatis.mapping.BoundSql;
import com.mybatis.mapping.MappedStatement;
import com.mybatis.session.ResultHandler;
import com.mybatis.session.RowBounds;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 默认的结果集处理器
 */
public class DefaultResultSetHandler implements ResultSetHandler {



    public DefaultResultSetHandler(Executor executor, MappedStatement mappedStatement, ParameterHandler parameterHandler, ResultHandler resultHandler, BoundSql boundSql, RowBounds rowBounds) {
    }

    @Override
    public <E> List<E> handlerResultSets(Statement stmt) throws SQLException {
        return null;
    }
}
