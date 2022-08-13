package com.mybatis.session;

public class DefaultResultContext<T> implements ResultContext<T> {

    private T resultObject;
    private int resultCount;
    private boolean stopped;

    public DefaultResultContext() {
        this.resultObject = null;
        this.resultCount = 0;
        this.stopped = false;
    }

    /**
     * 获取下一个查询结果
     * @param resultObject
     */
    public void nextResultObject(T resultObject){
        resultCount++;
        this.resultObject = resultObject;
    }

    @Override
    public T getResultObject() {
        return resultObject;
    }

    @Override
    public int getResultCount() {
        return resultCount;
    }

    @Override
    public boolean isStopped() {
        return stopped;
    }

    @Override
    public void stop() {
        this.stopped = true;
    }
}
