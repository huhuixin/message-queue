package com.hhx.rabbitmq.quickstart;

import com.hhx.rabbitmq.Util;

/**
 * 生产者
 * @author hhx
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        Util.consumer(channel -> {
            try {
                String message;
                //  发送数据
                for (int i = 0; i < 5; i++) {
                    message = "Hello RabbitMQ : " + i;
                    // 不指定exchange 会根据routingKey路由到名称匹配的queue
                    channel.basicPublish("", "test001", null, message.getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
