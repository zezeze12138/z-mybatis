package com.mybatis.mapping;

import com.mybatis.binding.SqlCommandType;
import com.mybatis.session.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MappedStatement {
    //就是mapper的方法名称
    private String id;
    //sql的类型
    private SqlCommandType type;
    //sql资源
    private SqlSource sqlSource;
    //配置信息
    private Configuration configuration;
    //Statement类型
    private StatementType statementType;
    //数据结果
    private List<ResultMap> resultMaps;

    public MappedStatement(String id, SqlCommandType type, SqlSource sqlSource, Configuration configuration) {
        this.id = id;
        this.type = type;
        this.sqlSource = sqlSource;
        this.configuration = configuration;
        this.statementType = StatementType.STATEMENT;
        this.resultMaps = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SqlCommandType getType() {
        return type;
    }

    public void setType(SqlCommandType type) {
        this.type = type;
    }

    public SqlSource getSqlSource() {
        return sqlSource;
    }

    public void setSqlSource(SqlSource sqlSource) {
        this.sqlSource = sqlSource;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public StatementType getStatementType() {
        return statementType;
    }

    public void setStatementType(StatementType statementType) {
        this.statementType = statementType;
    }

    public List<ResultMap> getResultMaps() {
        return resultMaps;
    }

    public void setResultMaps(List<ResultMap> resultMaps) {
        this.resultMaps = resultMaps;
    }

    /**
     * 绑定sql
     * @param parameterObject
     * @return
     */
    public BoundSql getBoundSql(Object parameterObject){
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        return boundSql;
    }
}
