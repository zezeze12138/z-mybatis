package com.mybatis.type;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库的数据类型枚举类
 */
public enum JdbcType {

    INTEGER(Types.INTEGER);

    public final int typeCode;
    private static Map<Integer, JdbcType> codeLookup = new HashMap<>();

    static {
        for(JdbcType jdbcType : JdbcType.values()){
            codeLookup.put(jdbcType.typeCode, jdbcType);
        }
    }

    JdbcType(int code) {
        this.typeCode = code;
    }

    /**
     * 通过code查询类型
     * @param code
     * @return
     */
    public static JdbcType forCode(int code){
        return codeLookup.get(code);
    }
}
