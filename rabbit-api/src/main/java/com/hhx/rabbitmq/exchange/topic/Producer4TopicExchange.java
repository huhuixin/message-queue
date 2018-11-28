package com.hhx.rabbitmq.exchange.topic;

import com.hhx.rabbitmq.Util;

/**
 * @author hhx
 */
public class Producer4TopicExchange {

    public static void main(String[] args) {
        Util.consumer(channel -> {
            try {
                String exchangeName = "test_topic_exchange";
                String routingKey1 = "user.save";
                String routingKey2 = "user.update";
                String routingKey3 = "user.delete.abc";
                String routingKey4 = "order.save";
                String routingKey5 = "order.update";
                String routingKey6 = "order.delete.abc";
                channel.basicPublish(exchangeName, routingKey1 , null , routingKey1.getBytes());
                channel.basicPublish(exchangeName, routingKey2 , null , routingKey2.getBytes());
                channel.basicPublish(exchangeName, routingKey3 , null , routingKey3.getBytes());
                channel.basicPublish(exchangeName, routingKey4 , null , routingKey4.getBytes());
                channel.basicPublish(exchangeName, routingKey5 , null , routingKey5.getBytes());
                channel.basicPublish(exchangeName, routingKey6 , null , routingKey6.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
