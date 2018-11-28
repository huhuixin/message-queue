package com.hhx.rabbitmq.message;

import com.hhx.rabbitmq.Util;
import com.rabbitmq.client.AMQP;

import java.util.Map;
import java.util.TreeMap;

/**
 * 消息携带附加属性
 * @author hhx
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        Util.consumer(channel -> {
            try {
                String message = "RabbitMQ Message";
                // 自定义消息头
                Map<String, Object> headers = new TreeMap<>();
                headers.put("my1", "111");
                headers.put("my2", "222");

                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        // 持久化模式
                        .deliveryMode(2)
                        // 字符集
                        .contentEncoding("UTF-8")
                        // 过期时间， 过期自动移除
                        .expiration("20000")
                        .headers(headers)
                        .build();
                channel.basicPublish("", "test001", properties, message.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
