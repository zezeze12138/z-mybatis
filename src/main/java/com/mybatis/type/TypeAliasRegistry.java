package com.mybatis.type;

import com.mybatis.io.Resource;

import java.sql.ResultSet;
import java.util.*;

/**
 * 类型别名注册
 */
public class TypeAliasRegistry {

    private final Map<String, Class<?>> typeAliases = new HashMap<>();

    /**
     * 构建时将常用的类型别名进行注册
     */
    public TypeAliasRegistry() {
        registerAlias("string", String.class);

        registerAlias("int", Integer.class);
        registerAlias("integer", Integer.class);

        registerAlias("object[]", Object[].class);

        registerAlias("map", Map.class);
        registerAlias("hashmap", HashMap.class);
        registerAlias("list", List.class);
        registerAlias("arraylist", ArrayList.class);
        registerAlias("collection", Collection.class);
        registerAlias("iterator", Iterator.class);

        registerAlias("ResultSet", ResultSet.class);
    }

    /**
     * 注册类别名
     * @param alias 别名名称
     * @param value 类
     */
    public void registerAlias(String alias, Class<?> value){
        if(alias == null){
            throw new RuntimeException("参数别名不能为空");
        }
        String key = alias.toLowerCase(Locale.ENGLISH);
        if(typeAliases.containsKey(key) && typeAliases.get(key) != null && !typeAliases.get(key).equals(value)){
            //如果已存在注册的类型，且通过类型key获取到得类型和注册的不同，则直接抛出异常
            throw new RuntimeException("类型已注册，传入类型与已注册的类型不同");
        }
        typeAliases.put(key, value);
    }

    /**
     * 解析别名返回类
     * @param alias 别名
     * @return 类
     */
    public <T> Class<T> resolveAlias(String alias){
        if(alias == null){
            return null;
        }
        String key = alias.toLowerCase(Locale.ENGLISH);
        Class<T> value;
        if(typeAliases.containsKey(alias)){
            value = (Class<T>) typeAliases.get(key);
        }else{
            //找不到的话通过类的项目路径找一下
            value = (Class<T>) Resource.classForName(alias);
        }
        return value;
    }
}
