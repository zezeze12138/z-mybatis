package com.mybatis.executor.resultset;

import com.mybatis.session.Configuration;
import com.mybatis.type.JdbcType;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 结果集包装
 */
public class ResultSetWrapper {

    private final ResultSet resultSet;
    private final Configuration configuration;
    private final List<String> columnNames = new ArrayList<>();
    private final List<String> classNames = new ArrayList<>();
    private final List<JdbcType> jdbcTypes = new ArrayList<>();

    public ResultSetWrapper(ResultSet resultSet, Configuration configuration) {
        this.resultSet = resultSet;
        this.configuration = configuration;
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            //这里查询的下标是从1开始的
            for(int i=1; i<=metaData.getColumnCount();i++){
                columnNames.add(metaData.getColumnName(i));
                classNames.add(metaData.getColumnClassName(i));
                jdbcTypes.add(JdbcType.forCode(metaData.getColumnType(i)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public List<String> getClassNames() {
        return classNames;
    }

    public List<JdbcType> getJdbcTypes() {
        return jdbcTypes;
    }
}
