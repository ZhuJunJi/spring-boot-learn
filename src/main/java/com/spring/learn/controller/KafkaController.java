package com.spring.learn.controller;

import com.alibaba.fastjson.JSONObject;
import com.spring.learn.common.entity.User;
import com.spring.learn.common.kafka.constant.Topic;
import com.spring.learn.common.kafka.producer.KafKaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author J.zhu
 * @date 2019/5/29
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

    /***
     * 发送消息体为bean的消息
     */
    @GetMapping("sendBean")
    public void sendBean() {
        User user = new User("shanshan",18);
        producer.sendMessage(Topic.BEAN, JSONObject.toJSONString(user));
    }


    /***
     * 多消费者组、组中多消费者对同一主题的消费情况
     */
    @GetMapping("sendGroup")
    public void sendGroup() {
        for (int i = 0; i < 4; i++) {
            producer.sendMessage(Topic.GROUP, i % 4, "key", "hello group " + i);
        }
    }
}
