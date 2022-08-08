package com.mybatis.executor.parameter;

import com.mybatis.mapping.BoundSql;
import com.mybatis.mapping.MappedStatement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 默认的入参参数处理器
 */
public class DefaultParameterHandler implements ParameterHandler {

    public DefaultParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {

    }

    @Override
    public Object getParameterObject() {
        return null;
    }

    @Override
    public void setParameters(PreparedStatement ps) throws SQLException {

    }
}
