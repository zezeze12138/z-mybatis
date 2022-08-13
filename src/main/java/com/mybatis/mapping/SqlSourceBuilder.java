package com.mybatis.mapping;

import com.mybatis.load.xml.GenericToeknParser;
import com.mybatis.load.xml.TokenHandler;
import com.mybatis.session.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * sql资源构建
 */
public class SqlSourceBuilder {

    private final Configuration configuration;

    public SqlSourceBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSource parse(String orginSql, Class<?> parameterType, Map<String, Object> additionParameters){
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
        GenericToeknParser parser = new GenericToeknParser("#{","}",handler);
        String sql = parser.parse(orginSql);
        return new StaticSqlSource(configuration, sql, handler.getParameterMappingList());
    }


    private static class ParameterMappingTokenHandler implements TokenHandler{
        private List<ParameterMapping>  parameterMappingList = new ArrayList<>();

        public List<ParameterMapping> getParameterMappingList() {
            return parameterMappingList;
        }

        //匹配时将#{}换成“?”占位符
        // TODO: 2022/8/13 这里其实还有其他的事情，例如需要把#{}中的变量名称都取出来。
        @Override
        public String handlerToken(String content) {
            return "?";
        }
    }
}
