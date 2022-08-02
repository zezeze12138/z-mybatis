package com.mybatis.session;

public interface ResultContext<T> {

    /**
     * 获取结果对象
     * @return
     */
    T getResultObject();

    /**
     * 获取结果数量
     * @return
     */
    int getResultCount();

    /**
     * 是否已停止？
     * @return
     */
    boolean isStopped();

    /**
     * 停止？
     */
    void stop();

}
