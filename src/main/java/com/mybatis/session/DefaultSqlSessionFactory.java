package com.mybatis.session;

import com.mybatis.executor.Executor;
import com.mybatis.mapping.Environment;
import com.mybatis.transaction.JdbcTranscationFactory;
import com.mybatis.transaction.Transaction;
import com.mybatis.transaction.TransactionFactory;

/**
 * 默认的SqlSessionFactory工厂
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    /**
     * 构造方法，传入配置信息
     * @param configuration
     */
    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 创建事务和执行器
     * @return
     */
    public SqlSession openSession() {
        Transaction transaction = null;
        final Environment environment = configuration.getEnvironment();
        final TransactionFactory transactionFactory = new JdbcTranscationFactory();
        try {
            transaction = transactionFactory.newTransaction(environment.getDataSource().getConnection());
            final Executor executor = configuration.newExecutor(transaction, ExecutorType.SIMPLE);
            return new DefaultSqlSession(configuration, executor);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("创建事务异常");
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
