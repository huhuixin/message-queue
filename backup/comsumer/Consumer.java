package com.hhx.rabbitmq.comsumer;

import com.hhx.rabbitmq.util.RabbitUtil;

/**
 * 自定义消费者
 * @author hhx
 */
public class Consumer {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            try {
                String exchangeName = "test_consumer_exchange";
                String routingKey = "consumer.#";
                String queueName = "test_consumer_queue";

                channel.exchangeDeclare(exchangeName, "topic", true, false, null);
                channel.queueDeclare(queueName, true, false, false, null);
                channel.queueBind(queueName, exchangeName, routingKey);

                channel.basicConsume(queueName, true, new MyConsumer(channel));
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
