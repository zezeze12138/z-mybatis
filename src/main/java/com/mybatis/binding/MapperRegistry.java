package com.mybatis.binding;

import com.mybatis.session.Configuration;
import com.mybatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Mapper注册类
 */
public class MapperRegistry {

    private final Configuration configuration;
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    public MapperRegistry(Configuration configuration) {
        this.configuration = configuration;
    }

    public<T> T getMapper(Class<T> type, SqlSession sqlSession){
        //通过代理的方式创建对应的Mapper
        MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
        if(mapperProxyFactory == null){
            throw new RuntimeException("未找到对应类型的Mapper代理工厂");
        }
        //创建Mapper实例
        return mapperProxyFactory.newInstance(sqlSession);
    }

    /**
     * 添加mapper
     * @param type
     * @param <T>
     */
    public <T> void addMapper(Class<T> type) {
        //使用map存储mapper类与MapperProxyFactory的关系，这样可以在获取Mapper时找到对应的代理工厂，创建代理Mapper。
        knownMappers.put(type, new MapperProxyFactory<>(type));
    }

    /**
     * 添加包路径下的mapper
     * @param packageName
     */
    public void addMapper(String packageName) {
//        /*try {
//            Class<?> clazz = Class.forName(packageName);
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }*/

    }
}
