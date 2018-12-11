package com.hhx.rabbitmq.message;

import com.hhx.rabbitmq.util.RabbitUtil;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 消费者
 * @author hhx
 */
public class Consumer {

    public static void main(String[] args) {
        RabbitUtil.consumer(channel -> {
            try {
                QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
                // 关联消费者和队列
                channel.basicConsume("test001", true, queueingConsumer);
                // 获取消息
                QueueingConsumer.Delivery delivery;
                delivery = queueingConsumer.nextDelivery();
                System.out.println("message: " + new String(delivery.getBody()));
                System.out.println("properties: " + delivery.getProperties());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
