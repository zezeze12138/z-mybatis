package com.mybatis.executor;

import com.mybatis.mapping.MappedStatement;
import com.mybatis.session.ResultHandler;

import java.util.List;

/**
 * sql执行器
 */
public interface Executor {

    /**
     * 数据列表查询
     * @param ms
     * @param parameter
     * @param resultHandler
     * @param <E>
     * @return
     */
    <E> List<E> query(MappedStatement ms, Object parameter, ResultHandler resultHandler);

}
