package com.mybatis.mapping;

import com.mybatis.load.document.SqlNode;
import com.mybatis.load.xml.DynamicContext;
import com.mybatis.session.Configuration;

public class DynamicSqlSource implements SqlSource {

    private final Configuration configuration;
    private final SqlNode sqlNode;

    public DynamicSqlSource(Configuration configuration, SqlNode sqlNode) {
        this.configuration = configuration;
        this.sqlNode = sqlNode;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        DynamicContext context = new DynamicContext(configuration, parameterObject);
        sqlNode.apply(context);
        Class<?> parameterType = Object.class;
        if(parameterObject != null){
            parameterType = parameterObject.getClass();
        }
        SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder(configuration);
        // TODO: 2022/8/13 这里少了，additionParameters为在context中取到的bindings
        SqlSource sqlSource = sqlSourceBuilder.parse(context.getSql(),parameterType, null);
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        // TODO: 2022/8/13 这里少了，往boundSql中绑定context的bingdings参数
        return boundSql;
    }
}
