package com.spring.learn.controller;

import com.spring.learn.common.entity.User;
import com.spring.learn.common.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author J.zhu
 * @date 2019/6/1
 */
@RequestMapping("/redis")
@RestController
@Slf4j
public class RedisController {

    @GetMapping("/saveUser/{userName}/{age}")
    @ResponseBody
    public User set(@PathVariable String userName, @PathVariable Integer age){
        RedisUtil.set(userName,new User(userName, age));
        return RedisUtil.get(userName);
    }

    @GetMapping("/getUser/{userName}")
    @ResponseBody
    public User get(@PathVariable String userName){
        return RedisUtil.get(userName);
    }
}
