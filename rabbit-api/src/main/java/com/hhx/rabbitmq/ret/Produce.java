package com.hhx.rabbitmq.ret;

import com.hhx.rabbitmq.Util;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ReturnListener;

import java.io.IOException;

/**
 * @author hhx
 */
public class Produce {

    public static void main(String[] args) {
        Util.consumer(channel -> {
            try {
                String exchangeName = "";
                String routingKey = "test_return";

                String msg = "Hello RabbitMQ!";
                // 开启异步监听
                channel.addReturnListener(new ReturnListener() {
                    @Override
                    public void handleReturn(int replyCode, String replyText, String exchange,
                                             String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {

                        System.err.println("---------handle  return----------");
                        System.err.println("replyCode: " + replyCode);
                        System.err.println("replyText: " + replyText);
                        System.err.println("exchange: " + exchange);
                        System.err.println("routingKey: " + routingKey);
                        System.err.println("properties: " + properties);
                        System.err.println("body: " + new String(body));
                    }
                });
                // 第三个参数 Mandatory设置为true 代表如果路由不到就返回给returnListener处理，设置为false就直接丢弃不处理
                channel.basicPublish(exchangeName, routingKey, true, null, "message1".getBytes());
                channel.basicPublish(exchangeName, routingKey, null, "message2".getBytes());
                channel.basicPublish(exchangeName, routingKey, false, null, "message3".getBytes());
                // 防止主线程终结
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
