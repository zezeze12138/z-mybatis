package com.mybatis.example.dao;

import com.mybatis.example.entity.User;

import java.util.List;

/**
 * 用户Mapper
 */
public interface UserDao {

    /**
     * 获取用户列表
     * @return 用户列表
     */
    List<User> getUserList();

}
