package com.mybatis.mapping;

/**
 * sql资源接口
 */
public interface SqlSource {

    BoundSql getBoundSql(Object parameterObject);

}
