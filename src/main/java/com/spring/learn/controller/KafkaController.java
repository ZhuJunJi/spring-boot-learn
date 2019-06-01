package com.spring.learn.controller;

import com.spring.learn.common.kafka.constant.Topic;
import com.spring.learn.common.kafka.producer.KafKaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by J.zhu on 2019/5/29.
 * Kafka Controller
 */
@RequestMapping("/kafka")
@RestController
@Slf4j
public class KafkaController {

    @Autowired
    private KafKaProducer producer;

    @SuppressWarnings("unchecked")
    @RequestMapping("/sendSimple/{message}")
    public String sendSimple(@PathVariable(name = "message") String message){
        producer.sendMessage(Topic.SIMPLE,message);
        return "success";
    }
}
