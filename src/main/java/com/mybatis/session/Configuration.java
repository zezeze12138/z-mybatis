package com.mybatis.session;

import com.mybatis.binding.MapperRegistry;
import com.mybatis.mapping.MappedStatement;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置信息类，作为整个mybatis信息存储的载体
 */
public class Configuration {

    //注册的Mapper
    protected final MapperRegistry mapperRegistry = new MapperRegistry(this);
    //Mapper的信息
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public<T> T getMapper(Class<T> type, SqlSession sqlSession){
        return mapperRegistry.getMapper(type, sqlSession);
    }

    public<T> void addMapper(Class<T> type){
        mapperRegistry.addMapper(type);
    }

    /**
     * 获取Mapper的信息内容
     * @param id
     * @return
     */
    public MappedStatement getMappedStatement(String id){
        return mappedStatements.get(id);
    }

}
