package com.mybatis.executor;

import com.mybatis.mapping.BoundSql;
import com.mybatis.mapping.MappedStatement;
import com.mybatis.session.Configuration;
import com.mybatis.session.ResultHandler;
import com.mybatis.session.RowBounds;
import com.mybatis.transaction.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 基础SQl执行器
 */
public abstract class BaseExecutor implements Executor{

    protected int queryStack;
    protected Executor wrapper;
    protected Configuration configuration;
    protected Transaction transaction;

    public BaseExecutor(Configuration configuration, Transaction transaction) {
        this.configuration = configuration;
        this.transaction = transaction;
        this.wrapper = this;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) {
        BoundSql boundSql = ms.getBoundSql(parameter);
        List<E> list = null;
        try{
            queryStack++;
            //这里后续可以加上在缓存中获取一下
            if(resultHandler == null){
                list = null;
            }
            if(list == null){
                list = queryFromDatabase(ms, parameter, rowBounds, resultHandler, boundSql);
            }
        }finally {
            queryStack--;
        }
        if(queryStack == 0){

        }
        return list;
    }

    protected <E> List<E> queryFromDatabase(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql){
        List<E> list;
        //这里可以增加缓存操作
        try{
            list = doQuery(ms, parameter, rowBounds, resultHandler, boundSql);
        }finally {

        }
        return list;
    }

    /**
     * 获取连接
     * @return
     */
    protected Connection getConnection(){
        Connection connection = transaction.getConnection();
        return connection;
    }

    /**
     * 关闭statement
     * @param statement
     */
    protected void closeStatement(Statement statement){
        if(statement != null){
            try{
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract <E> List<E> doQuery(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql);

}
