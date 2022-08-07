package com.mybatis.transaction;

import java.sql.Connection;
import java.util.Properties;

/**
 * 事务工厂接口
 */
public interface TransactionFactory {

    void setProperties(Properties properties);

    Transaction newTransaction(Connection conn);


}
