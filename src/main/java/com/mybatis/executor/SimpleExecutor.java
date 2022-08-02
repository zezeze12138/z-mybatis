package com.mybatis.executor;

import com.mybatis.mapping.MappedStatement;
import com.mybatis.session.ResultHandler;

import java.util.List;

public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, ResultHandler resultHandler) {
        return null;
    }
}
