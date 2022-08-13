package com.mybatis.session;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认的结果处理
 */
public class DefaultResultHandler implements ResultHandler<Object> {

    private final List<Object> list;

    public DefaultResultHandler() {
        this.list = new ArrayList<>();
    }

    @Override
    public void handlerResult(ResultContext<?> resultContext) {
        list.add(resultContext.getResultObject());
    }


    public List<Object> getResultList() {
        return list;
    }
}
