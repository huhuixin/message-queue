package com.hhx.rabbitmq.dlx;

import com.hhx.rabbitmq.util.RabbitUtil;
import com.rabbitmq.client.AMQP;

/**
 *
 * @author hhx
 */
public class Produce {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            try {
                String exchangeName = "test_dlx_exchange";
                String routingKey = "dlx.save";
                for(int i =0; i<5; i ++){
                    // 过期的消息会被丢进死信队列
                    AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                            .deliveryMode(2)
                            .contentEncoding("UTF-8")
                            // 过期时间
                            .expiration("8000")
                            .build();
                    channel.basicPublish(exchangeName, routingKey, properties, ("dlx-message---" + i).getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
