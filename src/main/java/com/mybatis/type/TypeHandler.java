package com.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 类型换换接口
 *
 *      javaType  ↓           ↑  javaType
 *        to      ↓           ↑  to
 *      jdbcType  ↓           ↑  javaType
 *                ↓           ↑
 * setParameter() ↓TypeHandler↑  getResult()
 *                ↓           ↑
 *                 ____________
 *                |  DataBase |
 *                |___________|
 */
public interface TypeHandler<T> {

    /**
     * 设置参数
     * 将javaType转为jdbcType
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     */
    void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType);

    /**
     * 获取结果值
     * 将jdbcType转为javaType
     * @param rs
     * @param columnName
     * @return
     */
    T getResult(ResultSet rs, String columnName);
}
