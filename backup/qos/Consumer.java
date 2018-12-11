package com.hhx.rabbitmq.qos;

import com.hhx.rabbitmq.util.RabbitUtil;

/**
 * 限流
 * @author hhx
 */
public class Consumer {

    public static void main(String[] args) {
        RabbitUtil.consumer(channel -> {
            try {
                String exchangeName = "test_qos_exchange";
                String routingKey = "qos.*";
                String queueName = "test_qos_queue";

                channel.exchangeDeclare(exchangeName, "topic", true, false, null);
                channel.queueDeclare(queueName, true, false, false, null);
                channel.queueBind(queueName, exchangeName, routingKey);
                // 每次最多只允许有不超过3条为ack的消息存在
                channel.basicQos(0, 3, false);
                // 不自动签收
                channel.basicConsume(queueName, false, new MyConsumer(channel));
                Thread.sleep(100000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
