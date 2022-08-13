package com.mybatis.type;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类型处理注册
 * 这里维护了Java数据类型和类型处理器的对应关系
 */
public class TypeHandlerRegistry {

    /**
     * 存放java数据类型与 Jdbc数据类型和转换器的关系
     */
    private final Map<Type, Map<JdbcType, TypeHandler<?>>> typeHandlerMap = new ConcurrentHashMap<>();

    public TypeHandlerRegistry(){
        //将不同的数据类型放入map中
        //int类型的
        register(Integer.class, new IntegerTypeHandler());
        register(int.class, new IntegerTypeHandler());
        //string类型的
        register(String.class, new StringTypeHandler());


    }

    private <T> void register(Class<T> javaType, TypeHandler<? extends T> typeHandler) {
        Map<JdbcType, TypeHandler<?>> jdbcTypeTypeHandlerMap = typeHandlerMap.get(javaType);
        if(jdbcTypeTypeHandlerMap == null){
            jdbcTypeTypeHandlerMap = new HashMap<>();
        }
        jdbcTypeTypeHandlerMap.put(null, typeHandler);
        typeHandlerMap.put(javaType, jdbcTypeTypeHandlerMap);
    }

    private <T> void register(Class<T> javaType, JdbcType jdbcType, TypeHandler<? extends T> typeHandler) {
        Map<JdbcType, TypeHandler<?>> jdbcTypeTypeHandlerMap = typeHandlerMap.get(javaType);
        if(jdbcTypeTypeHandlerMap == null){
            jdbcTypeTypeHandlerMap = new HashMap<>();
        }
        jdbcTypeTypeHandlerMap.put(jdbcType, typeHandler);
        typeHandlerMap.put(javaType, jdbcTypeTypeHandlerMap);
    }

    public TypeHandler getTypeHander(Class type){
        return typeHandlerMap.get(type).get(null);
    }
}
