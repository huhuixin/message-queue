package com.hhx.rabbitmq.confirm;

import com.hhx.rabbitmq.util.RabbitUtil;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
/**
 * @author hhx
 */
public class Consumer {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            try {
                String exchangeName = "test_confirm_exchange";
                String routingKey = "confirm.#";
                String queueName = "test_confirm_queue";
                QueueingConsumer consumer = RabbitUtil
                        .declareQueueAndBindExchange(channel, exchangeName, "topic", queueName, routingKey);
                while (true) {
                    Delivery delivery = consumer.nextDelivery();
                    String msg = new String(delivery.getBody());
                    System.err.println("消费端: " + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
