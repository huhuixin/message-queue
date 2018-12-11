package com.hhx.rabbitmq.exchange.topic;

import com.hhx.rabbitmq.util.RabbitUtil;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @author hhx
 */
public class Consumer4TopicExchange {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            try {
                String exchangeName = "test_topic_exchange";
                String exchangeType = "topic";
                String queueName1 = "test_topic_queue1";
                String queueName2 = "test_topic_queue2";

                String routingKey1 = "user.*";
                String routingKey2 = "*.save";

                QueueingConsumer consumer = RabbitUtil
                        .declareQueueAndBindExchange(channel, exchangeName, exchangeType, queueName2, routingKey2);
                        //.declareQueueAndBindExchange(channel, exchangeName, exchangeType, queueName1, routingKey1);

                //循环获取消息
                while(true){
                    //获取消息，如果没有消息，这一步将会一直阻塞
                    QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                    String msg = new String(delivery.getBody());
                    System.out.println("收到消息：" + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
