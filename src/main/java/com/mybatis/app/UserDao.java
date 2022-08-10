package com.mybatis.app;

import java.util.List;
import java.util.Map;

/**
 * 用户DAO
 */
public interface UserDao {

    List<Map<String, Object>> getUser();
}
