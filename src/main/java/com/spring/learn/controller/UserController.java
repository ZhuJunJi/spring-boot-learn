package com.spring.learn.controller;

import com.spring.learn.common.entity.User;
import com.spring.learn.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author J.zhu
 * @date 2019/6/3
 * 用户 Controller
 */
@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/save/{userName}/{age}")
    @ResponseBody
    public String createUser(@PathVariable String userName, @PathVariable Integer age){
        User user = new User(userName, age);
        userService.createUser(user);
        return "Success";
    }

    @GetMapping("/get/{userId}")
    @ResponseBody
    public String createUser(@PathVariable Integer userId) {
        if(userId == 2){
            RuntimeException runtimeException = new RuntimeException("错误日志测试");
            log.error("错误日志测试",runtimeException);
            throw runtimeException;
        }
        User user = userService.getUserById(userId);
        log.debug("user:{}",user.toString());
        log.error("user:{}",user.toString());

        return user.toString();
    }
}
