package com.mybatis.binding;

import com.mybatis.session.Configuration;
import com.mybatis.session.SqlSession;

/**
 * Mapper注册类
 */
public class MapperRegistry {

    private final Configuration configuration;

    public MapperRegistry(Configuration configuration) {
        this.configuration = configuration;
    }

    public<T> T getMapper(Class<T> type, SqlSession sqlSession){
        //通过代理的方式创建对应的Mapper
        return null;
    }



}
