package com.mybatis.session;

import com.mybatis.binding.MapperRegistry;
import com.mybatis.executor.Executor;
import com.mybatis.executor.SimpleExecutor;
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

    /**
     * 创建执行器
     * @param executorType
     * @return
     */
    public Executor newExecutor(ExecutorType executorType){
        Executor executor = null;
        if(executorType.SIMPLE == executorType){
            executor = new SimpleExecutor();
        }
        // TODO: 2022/8/2  源码中还有一个拦截链，通过拦截链往创建的执行器中添加插件处理
        //这里暂时线不做实现拦截链的实现
        return executor;
    }
}
