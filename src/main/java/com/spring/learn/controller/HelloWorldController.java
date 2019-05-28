package com.spring.learn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by J.zhu on 2019/5/28.
 * Hello World Controller
 */
@SuppressWarnings("unchecked")
@RestController
public class HelloWorldController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @RequestMapping("/hello")
    public String hello(){
        return "Hello Spring Boot！";
    }

    @RequestMapping("/send/{message}")
    public String send(@PathVariable(name = "message") String message){
        kafkaTemplate.send("message", message);
        return "success";
    }
}
