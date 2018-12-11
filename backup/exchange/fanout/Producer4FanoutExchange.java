package com.hhx.rabbitmq.exchange.fanout;

import com.hhx.rabbitmq.util.RabbitUtil;

/**
 * @author hhx
 */
public class Producer4FanoutExchange {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            try {
                // 声明
                String exchangeName = "test_fanout_exchange";
                String routingKey = "";
                //5 发送
                for(int i = 0; i < 10; i ++) {
                    String msg = "Hello World RabbitMQ 4 FANOUT Exchange Message ...";
                    channel.basicPublish(exchangeName, routingKey, null , msg.getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
