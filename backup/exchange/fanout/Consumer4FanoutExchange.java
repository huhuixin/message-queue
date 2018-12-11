package com.hhx.rabbitmq.exchange.fanout;

import com.hhx.rabbitmq.util.RabbitUtil;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @author hhx
 */
public class Consumer4FanoutExchange {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            try {
                String queueName = "test_fanout_queue1";
                String exchangeName = "test_fanout_exchange";
                String exchangeType = "fanout";
                String routingKey = "";
                QueueingConsumer consumer = RabbitUtil
                        .declareQueueAndBindExchange(channel, exchangeName, exchangeType, queueName, routingKey);
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
