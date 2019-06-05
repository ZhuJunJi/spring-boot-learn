package com.spring.learn.common.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 *
 * @author J.zhu
 * @date 2019/5/29
 * @description : kafka 生产者
 */
@Component
@Slf4j
public class KafKaProducer {

        @Autowired
        private KafkaTemplate kafkaTemplate;

        @SuppressWarnings("unchecked")
        public void sendMessage(String topic, Object object){
            /*
             * 这里的ListenableFuture类是spring对java原生Future的扩展增强,是一个泛型接口,用于监听异步方法的回调
             * 而对于kafka send 方法返回值而言，这里的泛型所代表的实际类型就是 SendResult<K, V>,而这里K,V的泛型实际上
             * 被用于ProducerRecord<K, V> producerRecord,即生产者发送消息的key,value 类型
             */
            ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic,object);

            future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.info("发送消息失败:" + throwable.getMessage());
                }

                @Override
                public void onSuccess(SendResult<String, Object> sendResult) {
                    log.info("发送消息成功:" + sendResult.toString());
                }
            });
        }

    /**
     * 发送分组消息
     * @param topic 主题
     * @param partition 组
     * @param object 消息
     */
    @SuppressWarnings("unchecked")
    public void sendMessage(String topic, Integer partition, String key, Object object){
        /*
         * 这里的ListenableFuture类是spring对java原生Future的扩展增强,是一个泛型接口,用于监听异步方法的回调
         * 而对于kafka send 方法返回值而言，这里的泛型所代表的实际类型就是 SendResult<K, V>,而这里K,V的泛型实际上
         * 被用于ProducerRecord<K, V> producerRecord,即生产者发送消息的key,value 类型
         */
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic,partition,key,object);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info("发送消息失败:" + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> sendResult) {
                log.info("发送消息成功:" + sendResult.toString());
            }
        });
    }
}
