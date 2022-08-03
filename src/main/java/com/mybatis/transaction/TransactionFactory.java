package com.mybatis.transaction;

import java.sql.Connection;

/**
 * 事务工厂接口
 */
public interface TransactionFactory {

    Transaction newTransaction(Connection conn);

}
