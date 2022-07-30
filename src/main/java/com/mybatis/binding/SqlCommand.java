package com.mybatis.binding;

import com.mybatis.mapping.MappedStatement;
import com.mybatis.session.Configuration;

import java.lang.reflect.Method;

public class SqlCommand {

    private final String name;
    private final SqlCommandType type;


    public SqlCommand(Configuration configuration, Class<?> mapperInterface, Method method) {
        final String methodName = method.getName();
        final Class<?> declaringClass = method.getDeclaringClass();
        MappedStatement mappedStatement = resolveMappedStatement(mapperInterface, methodName, declaringClass, configuration);
        if(mappedStatement != null){
            this.name = mappedStatement.getId();
            this.type = mappedStatement.getType();
        }else {
            this.name = null;
            this.type = SqlCommandType.UNKNOWN;
        }
    }

    /**
     * 解析并获取MappedStatement
     * @param mapperInterface mapper接口类
     * @param methodName 方法名称
     * @param declaringClass 声明的类
     * @param configuration 配置类
     * @return
     */
    private MappedStatement resolveMappedStatement(Class<?> mapperInterface, String methodName, Class<?> declaringClass, Configuration configuration){
        //接口名+方法名
        String statementId = mapperInterface.getName()+"."+methodName;
        return configuration.getMappedStatement(statementId);
    }

}
