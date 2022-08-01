package com.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Integer类型的数据转换
 */
public class IntegerTypeHandler implements TypeHandler<Integer> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Integer parameter, JdbcType jdbcType) {
        try {
            //将参数使用PreparedStatement的setInt塞入进去，完成映射关系
            ps.setInt(i, parameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer getResult(ResultSet rs, String columnName) {
        try {
            //将数据库数据类型转换为java数据类型
            int result = rs.getInt(columnName);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
