package com.hhx.rabbitmq.ack;

import com.hhx.rabbitmq.util.RabbitUtil;

/**
 * 应答 ack 与 nack 与 消息重回队列
 * @author hhx
 */
public class Consumer {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            try {
                String exchangeName = "test_ack_exchange";
                String routingKey = "ack.*";
                String queueName = "test_ack_queue";

                channel.exchangeDeclare(exchangeName, "topic", true, false, null);
                channel.queueDeclare(queueName, true, false, false, null);
                channel.queueBind(queueName, exchangeName, routingKey);
                // 不自动签收
                channel.basicConsume(queueName, false, new MyConsumer(channel));
                Thread.sleep(100000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
