package com.spring.learn.common.kafka.consumer;

import com.spring.learn.common.kafka.constant.Topic;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


/**
 *
 * @author J.zhu
 * @date 2019/5/29
 */
@Component
@Slf4j
public class KafkaSimpleConsumer {

    /**
     * 简单消费者
     * @param record
     * @param topic
     * @param consumer
     */
    @KafkaListener(groupId = "simpleGroup", topics = Topic.SIMPLE)
    public void consumer1G1(ConsumerRecord<String, Object> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Consumer consumer) {
        System.out.println("消费者收到消息:" + record.value() + "; topic:" + topic);
        /*
         * 如果需要手工提交异步 consumer.commitSync();
         * 手工同步提交 consumer.commitAsync()
         */
    }
}
