package com.mybatis.binding;

import com.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Mapper代理类，通过代理调用对应DmapperMethod方法。
 * @param <T>
 */
public class MapperProxy<T> implements InvocationHandler {

    private final SqlSession sqlSession;
    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperMethod mapperMethod =  new MapperMethod(mapperInterface, method, sqlSession.getConfiguration());
        return mapperMethod.execute(sqlSession, args);
    }

}
