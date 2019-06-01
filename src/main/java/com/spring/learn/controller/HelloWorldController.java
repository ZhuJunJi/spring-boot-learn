package com.spring.learn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by J.zhu on 2019/5/28.
 * Hello World Controller
 */
@SuppressWarnings("unchecked")
@RestController
@Slf4j
public class HelloWorldController {

    @RequestMapping("/hello")
    public String hello(){
        return "Hello Spring Boot！";
    }

}
