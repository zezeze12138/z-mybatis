package com.mybatis.session;

import com.mybatis.binding.MapperRegistry;
import com.mybatis.datasource.pooled.PooledDataSourceFactory;
import com.mybatis.datasource.unpooled.UnpooledDataSourceFactory;
import com.mybatis.executor.Executor;
import com.mybatis.executor.SimpleExecutor;
import com.mybatis.executor.parameter.DefaultParameterHandler;
import com.mybatis.executor.parameter.ParameterHandler;
import com.mybatis.executor.resultset.DefaultResultSetHandler;
import com.mybatis.executor.resultset.ResultSetHandler;
import com.mybatis.executor.statement.RoutingStatementHandler;
import com.mybatis.executor.statement.StatementHandler;
import com.mybatis.mapping.BoundSql;
import com.mybatis.mapping.Environment;
import com.mybatis.mapping.MappedStatement;
import com.mybatis.transaction.JdbcTranscationFactory;
import com.mybatis.transaction.Transaction;
import com.mybatis.type.TypeAliasRegistry;
import com.mybatis.type.TypeHandlerRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置信息类，作为整个mybatis信息存储的载体
 */
public class Configuration {

    //数据源环境信息
    protected Environment environment;
    //注册的Mapper
    protected final MapperRegistry mapperRegistry = new MapperRegistry(this);
    //Mapper的信息
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();
    //类别名注册
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
    //类型处理注册
    protected final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();

    public<T> T getMapper(Class<T> type, SqlSession sqlSession){
        return mapperRegistry.getMapper(type, sqlSession);
    }

    public<T> void addMapper(Class<T> type){
        mapperRegistry.addMapper(type);
    }

    /**
     * 构建时注册类别名
     */
    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JdbcTranscationFactory.class);
        typeAliasRegistry.registerAlias("POOLED", PooledDataSourceFactory.class);
        typeAliasRegistry.registerAlias("UNPOOLED", UnpooledDataSourceFactory.class);
    }

    /**
     * 添加Mapper到Mapper注册器中
     * @param packageName
     * @param <T>
     */
    public <T> void addMappers(String packageName){
        mapperRegistry.addMapper(packageName);
    }

    /**
     * 获取Mapper的信息内容
     * @param id
     * @return
     */
    public MappedStatement getMappedStatement(String id){
        return mappedStatements.get(id);
    }

    /**
     * 获取类别名注册器
     * @return
     */
    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }

    /**
     * 获取类型处理注册器
     * @return
     */
    public TypeHandlerRegistry getTypeHandlerRegistry() {
        return typeHandlerRegistry;
    }

    /**
     * 创建执行器
     * @param executorType
     * @return
     */
    public Executor newExecutor(Transaction transaction, ExecutorType executorType){
        Executor executor = null;
        if(executorType.SIMPLE == executorType){
            executor = new SimpleExecutor(this, transaction);
        }
        // TODO: 2022/8/2  源码中还有一个拦截链，通过拦截链往创建的执行器中添加插件处理
        //这里暂时线不做实现拦截链的实现
        return executor;
    }

    /**
     * 创建新的StatementHandler
     * @param executor
     * @param ms
     * @param parameter
     * @param rowBounds
     * @param resultHandler
     * @param boundSql
     * @return
     */
    public StatementHandler newStatementHandler(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql){
        StatementHandler statementHandler = new RoutingStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql);
        // TODO: 2022/8/8 源码中还有一个拦截链，通过拦截链往创建的执行器中添加插件处理
        // 这里暂时线不做实现拦截链的实现
        return statementHandler;
    }

    /**
     * 入参参数处理器
     * @param mappedStatement
     * @param parameterObject
     * @param boundSql
     * @return
     */
    public ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql){
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        // TODO: 2022/8/8 源码中还有一个拦截链，通过拦截链往创建的执行器中添加插件处理
        // 这里暂时线不做实现拦截链的实现
        return parameterHandler;
    }

    /**
     * 创建结果集处理器
     * @param executor
     * @param mappedStatement
     * @param rowBounds
     * @param parameterHandler
     * @param resultHandler
     * @param boundSql
     * @return
     */
    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, RowBounds rowBounds, ParameterHandler parameterHandler, ResultHandler resultHandler, BoundSql boundSql){
        ResultSetHandler resultSetHandler = new DefaultResultSetHandler(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);
        // TODO: 2022/8/8 源码中还有一个拦截链，通过拦截链往创建的执行器中添加插件处理
        // 这里暂时线不做实现拦截链的实现
        return resultSetHandler;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
