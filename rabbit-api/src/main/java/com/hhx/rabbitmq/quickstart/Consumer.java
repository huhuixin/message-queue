package com.hhx.rabbitmq.quickstart;

import com.hhx.rabbitmq.Util;
import com.rabbitmq.client.*;

/**
 * 消费者
 * @author hhx
 */
public class Consumer {

    public static void main(String[] args) {
        Util.consumer(channel -> {
            try {
                QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
                // 关联消费者和队列
                channel.basicConsume("test001", true, queueingConsumer);
                // 获取消息
                QueueingConsumer.Delivery delivery;
                for (int i = 0; i < 5; i++) {
                    delivery = queueingConsumer.nextDelivery();
                    System.out.println("consumer: " + new String(delivery.getBody()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
