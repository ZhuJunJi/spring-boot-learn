package com.spring.learn.common.kafka.consumer;

import com.spring.learn.common.kafka.constant.Topic;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

/**
 * @author J.zhu
 * @date 2019/5/29
 * @description : kafka 消费者组
 * <p>
 * 多个消费者群组可以共同读取同一个主题，彼此之间互不影响。
 */
@Component
@Slf4j
public class KafkaGroupConsumer {

    /**
     * 分组1 中的消费者1 消费 0分区，1分区
     * @param record
     */
    @KafkaListener(id = "consumer1-1", groupId = "group1", topicPartitions =
            {@TopicPartition(topic = Topic.GROUP, partitions = {"0", "1"})
            })
    public void consumer1_1(ConsumerRecord<String, Object> record) {
        System.out.println("consumer1-1 收到消息:" + record.value());
    }

    /**
     * 分组1 中的消费者2 消费 2分区
     * @param record
     */
    @KafkaListener(id = "consumer1-2", groupId = "group1", topicPartitions =
            {@TopicPartition(topic = Topic.GROUP, partitions = {"2"})
            })
    public void consumer1_2(ConsumerRecord<String, Object> record) {
        System.out.println("consumer1-2 收到消息:" + record.value());
    }

    /**
     * 分组1 中的消费者3 消费 3分区
     * @param record
     */
    @KafkaListener(id = "consumer1-3", groupId = "group1", topicPartitions =
            {@TopicPartition(topic = Topic.GROUP, partitions = {"3"})
            })
    public void consumer1_3(ConsumerRecord<String, Object> record) {
        System.out.println("consumer1-3 收到消息:" + record.value());
    }

    /**
     * 分组2 中的消费者 消费 0分区，1分区，2分区，3分区
     * @param record
     */
    @KafkaListener(id = "consumer2-1", groupId = "group2", topicPartitions =
            {@TopicPartition(topic = Topic.GROUP, partitions = {"0","1","2", "3"})
            })
    public void consumer2_1(ConsumerRecord<String, Object> record) {
        System.err.println("consumer2-1 收到消息:" + record.value());
    }
}
