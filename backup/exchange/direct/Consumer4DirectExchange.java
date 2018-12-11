package com.hhx.rabbitmq.exchange.direct;

import com.hhx.rabbitmq.util.RabbitUtil;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 直连
 * @author hhx
 */
public class Consumer4DirectExchange {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            try {
                String exchangeName = "test_direct_exchange";
                String exchangeType = "direct";
                String queueName = "test_direct_queue";
                String routingKey = "test.direct";
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
