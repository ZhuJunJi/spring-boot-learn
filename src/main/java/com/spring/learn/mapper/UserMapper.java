package com.spring.learn.mapper;

import com.spring.learn.common.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author J.zhu
 * @date 2019/6/3
 * 用户 Mapper
 */
@Repository
public interface UserMapper {

    /**
     * 保存用户信息
     * @param user 用户信息
     * @return Integer 
     */
    Integer insert(User user);

    /**
     * 通过用户ID获取用户信息
     * @param userId 用户ID
     * @return User 用户信息
     */
    User getById(@Param("userId") Integer userId);
}
