package com.hhx.rabbitmq.exchange.direct;

import com.hhx.rabbitmq.Util;

/**
 * @author hhx
 */
public class Producer4DirectExchange {

    public static void main(String[] args) {
        Util.consumer(channel -> {
            try {
                // 声明
                String exchangeName = "test_direct_exchange";
                String routingKey = "test.direct";
                // 发送
                String msg = "Hello World RabbitMQ 4  Direct Exchange Message 111 ... ";
                channel.basicPublish(exchangeName, routingKey , null , msg.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
