package com.mybatis.binding;

import com.mybatis.session.Configuration;
import com.mybatis.session.SqlSession;

import java.lang.reflect.Method;

public class MapperMethod {

    private final SqlCommand command;
    private final Method method;

    public MapperMethod(Class<?> mapperInterface, Method method, Configuration config) {
        this.command = new SqlCommand(config, mapperInterface, method);
        this.method = method;
    }

    /**
     * sql执行
     * @param sqlSession
     * @param args
     * @return
     */
    public Object execute(SqlSession sqlSession, Object[] args){
        Object result = null;
        //匹配不同的sql命令类型，对sql进行执行处理
        switch (command.getType()){
            case SELECT:{
                //调用sqlSession中的方法，查询数据内容。
                result = sqlSession.selectList(command.getName(), args);
                break;
            }
            default:
                throw new RuntimeException("未找到可用的sql命令类型");
        }
        return result;
    }
}
