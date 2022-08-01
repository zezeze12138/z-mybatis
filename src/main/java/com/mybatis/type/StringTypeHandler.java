package com.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * String类型的转换映射处理器
 */
public class StringTypeHandler implements TypeHandler<String> {
    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) {
        try {
            ps.setString(i, parameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getResult(ResultSet rs, String columnName) {
        try {
            return rs.getString(columnName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
