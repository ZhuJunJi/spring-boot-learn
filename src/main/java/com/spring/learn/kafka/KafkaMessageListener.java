package com.spring.learn.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by J.zhu on 2019/5/28.
 * Kafka 消息监听
 */
@Component
public class KafkaMessageListener {

    @KafkaListener(topics = "message")
    public void listen(ConsumerRecord<?, ?> record) {
        String message = (String) record.value();
        System.out.println(message);
    }
}
