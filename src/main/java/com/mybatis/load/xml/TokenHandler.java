package com.mybatis.load.xml;

public interface TokenHandler {

    /**
     * 对传进来的content内容进行处理
     * @param content 内容信息
     * @return 处理结果
     */
    String handlerToken(String content);

}
