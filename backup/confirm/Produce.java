package com.hhx.rabbitmq.confirm;

import com.hhx.rabbitmq.util.RabbitUtil;
import com.rabbitmq.client.ConfirmListener;

import java.io.IOException;

/**
 * 消息确认模式，发送后通过异步回调机制确认消息是否被成功消费
 * @author hhx
 */
public class Produce {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            try {
                // 指定我们的消息投递模式: 消息的确认模式
                channel.confirmSelect();

                String exchangeName = "test_confirm_exchange";
                String routingKey = "confirm.save";

                String msg = "Hello RabbitMQ Send confirm message!";
                channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());
                // 开启异步监听
                channel.addConfirmListener(new ConfirmListener() {
                    @Override
                    public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                        System.err.println("-------no ack!-----------");
                    }

                    @Override
                    public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                        System.err.println("-------ack!-----------");
                    }
                });
                // 防止主线程终结
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
