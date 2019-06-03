package com.spring.learn.service.impl;

import com.spring.learn.common.db.annotation.Master;
import com.spring.learn.common.db.annotation.Slave;
import com.spring.learn.common.entity.User;
import com.spring.learn.mapper.UserMapper;
import com.spring.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author J.zhu
 * @date 2019/6/3
 * 用户 Service 实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Master
    @Override
    public Integer createUser(User user) {
        return userMapper.insert(user);
    }

    @Slave
    @Override
    public User getUserById(Integer userId) {
        return userMapper.getById(userId);
    }
}
