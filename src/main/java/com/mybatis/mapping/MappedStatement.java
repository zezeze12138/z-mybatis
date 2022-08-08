package com.mybatis.mapping;

import com.mybatis.binding.SqlCommandType;
import com.mybatis.session.Configuration;

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
