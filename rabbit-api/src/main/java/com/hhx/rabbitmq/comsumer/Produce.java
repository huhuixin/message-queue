package com.hhx.rabbitmq.comsumer;

import com.hhx.rabbitmq.Util;

/**
 * 消息确认模式，发送后通过异步回调机制确认消息是否被成功消费
 * @author hhx
 */
public class Produce {

    public static void main(String[] args) {
        Util.consumer(channel -> {
            try {
                String exchangeName = "test_consumer_exchange";
                String routingKey = "consumer.save";
                for(int i =0; i<5; i ++){
                    channel.basicPublish(exchangeName, routingKey, null, "consumer-message".getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
