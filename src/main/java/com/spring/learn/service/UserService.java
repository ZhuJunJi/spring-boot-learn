package com.spring.learn.service;

import com.spring.learn.common.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author J.zhu
 * @date 2019/6/3
 * 用户 Service
 */
public interface UserService {

    /**
     * 创建用户
     * @param user 用户信息
     * @return Integer
     */
    Integer createUser(@Param("user") User user);

    /**
     * 用户ID获取用户信息
     * @param userId 用户ID
     * @return User 用户信息
     */
    User getUserById(Integer userId);
}
