package com.hhx.rabbitmq.confirm;

import com.hhx.rabbitmq.Util;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
/**
 * @author hhx
 */
public class Consumer {

    public static void main(String[] args) {
        Util.consumer(channel -> {
            try {
                String exchangeName = "test_confirm_exchange";
                String routingKey = "confirm.#";
                String queueName = "test_confirm_queue";
                QueueingConsumer consumer = Util
                        .createQueueAndBind(channel, exchangeName, "topic", queueName, routingKey);
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
