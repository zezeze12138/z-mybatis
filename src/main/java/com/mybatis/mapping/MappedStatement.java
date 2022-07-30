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
}
