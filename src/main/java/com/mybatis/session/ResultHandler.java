package com.mybatis.session;

/**
 * sql执行结果处理器
 */
public interface ResultHandler<T> {

    /**
     * 结果处理执行方法
     * @param resultContext
     */
    void handlerResult(ResultContext<? extends T> resultContext);

}
