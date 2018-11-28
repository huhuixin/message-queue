package com.hhx.rabbitmq.exchange.direct;

import com.hhx.rabbitmq.Util;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 直连
 * @author hhx
 */
public class Consumer4DirectExchange {

    public static void main(String[] args) {
        Util.consumer(channel -> {
            try {
                String exchangeName = "test_direct_exchange";
                String exchangeType = "direct";
                String queueName = "test_direct_queue";
                String routingKey = "test.direct";

                //表示声明了一个交换机
                channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
                //表示声明了一个队列
                channel.queueDeclare(queueName, false, false, false, null);
                //建立一个绑定关系   发送到 ${exchangeName} 且 routingKey为 ${routingKey} 的消息会被路由到 ${queueName}
                channel.queueBind(queueName, exchangeName, routingKey);

                //durable 是否持久化消息
                QueueingConsumer consumer = new QueueingConsumer(channel);
                //参数：队列名称、是否自动ACK(签收)、Consumer
                channel.basicConsume(queueName, true, consumer);
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
