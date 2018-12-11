package com.hhx.rabbitmq.exchange.direct;

import com.hhx.rabbitmq.util.RabbitUtil;

/**
 * @author hhx
 */
public class Producer4DirectExchange {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
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
