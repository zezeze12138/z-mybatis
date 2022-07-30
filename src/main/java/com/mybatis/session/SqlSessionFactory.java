package com.mybatis.session;

/**
 * 创建SqlSession的工厂
 */
public interface SqlSessionFactory {

    /**
     * 打开一个SqlSession
     * @return
     */
    SqlSession openSession();

}
