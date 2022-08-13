package com.mybatis.executor.resultset;

import com.mybatis.executor.Executor;
import com.mybatis.executor.parameter.ParameterHandler;
import com.mybatis.io.Resource;
import com.mybatis.mapping.BoundSql;
import com.mybatis.mapping.MappedStatement;
import com.mybatis.mapping.ResultMap;
import com.mybatis.session.*;
import com.mybatis.type.TypeHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认的结果集处理器
 */
public class DefaultResultSetHandler implements ResultSetHandler {

    private final MappedStatement mappedStatement;
    private final Configuration configuration;

    public DefaultResultSetHandler(Executor executor, MappedStatement mappedStatement, ParameterHandler parameterHandler, ResultHandler resultHandler, BoundSql boundSql, RowBounds rowBounds) {
        this.mappedStatement = mappedStatement;
        this.configuration = mappedStatement.getConfiguration();
    }

    /**
     * 处理sql执行后返回的结果
     * @param stmt
     * @return
     * @throws SQLException
     */
    @Override
    public List<Object> handlerResultSets(Statement stmt) throws SQLException {
        final List<Object> multipleResults = new ArrayList<>();
        ResultSetWrapper resultSetWrapper = getFirstResultSet(stmt);
        List<ResultMap> resultMaps = mappedStatement.getResultMaps();
        handlerResultSets(resultSetWrapper, resultMaps, multipleResults);
        if(multipleResults.size() == 1){
            return (List<Object>) multipleResults.get(0);
        }
        return multipleResults;
    }

    private void handlerResultSets(ResultSetWrapper resultSetWrapper, List<ResultMap> resultMaps, List<Object> multipleResults) {
        DefaultResultHandler defaultResultHandler = new DefaultResultHandler();
        handlerRowValues(resultSetWrapper, resultMaps, defaultResultHandler);
        multipleResults.add(defaultResultHandler.getResultList());
    }

    private void handlerRowValues(ResultSetWrapper resultSetWrapper, List<ResultMap> resultMaps, ResultHandler resultHandler) {
        DefaultResultContext<Object> resultContext = new DefaultResultContext<>();
        ResultSet resultSet = resultSetWrapper.getResultSet();
        try{
            //遍历结果集
            while(resultSet.next()){
                Object rowValue = getRowValue(resultSetWrapper);
                //将结果内容放入结果处理上下文中
                resultContext.nextResultObject(rowValue);
                //使用处理方法处理结果
                resultHandler.handlerResult(resultContext);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Object getRowValue(ResultSetWrapper resultSetWrapper) {
        ResultSet resultSet = resultSetWrapper.getResultSet();
        //这里先用map存放结果
        Map<String,Object> resultMap = new HashMap<>();
        List<String> columnNames = resultSetWrapper.getColumnNames();
        for(int i=0;i<columnNames.size();i++){
            String classType = resultSetWrapper.getClassNames().get(i);
            Class<?> clazz = Resource.classForName(classType);
            TypeHandler<?> typeHander = configuration.getTypeHandlerRegistry().getTypeHander(clazz);
            Object result = typeHander.getResult(resultSetWrapper.getResultSet(), columnNames.get(i));
            resultMap.put(columnNames.get(i), result);
        }
        return resultMap;
    }

    private ResultSetWrapper getFirstResultSet(Statement stmt) {
        ResultSetWrapper resultSetWrapper = null;
        if(stmt != null){
            try {
                ResultSet resultSet = stmt.getResultSet();
                if(resultSet != null){
                    resultSetWrapper = new ResultSetWrapper(resultSet, configuration);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultSetWrapper;
    }
}
