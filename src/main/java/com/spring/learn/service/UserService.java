package com.spring.learn.service;

import com.spring.learn.common.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by J.zhu on 2019/6/3.
 * 用户 Service
 */
public interface UserService {

    Integer createUser(@Param("user") User user);

    User getUserById(Integer userId);
}
