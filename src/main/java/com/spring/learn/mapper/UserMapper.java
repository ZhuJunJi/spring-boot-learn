package com.spring.learn.mapper;

import com.spring.learn.common.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by J.zhu on 2019/6/3.
 * 用户 Mapper
 */
@Repository
public interface UserMapper {

    Integer insert(User user);

    User getById(@Param("userId") Integer userId);
}
