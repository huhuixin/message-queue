package com.hhx.rabbitmq.api.message.ack;

import com.hhx.rabbitmq.util.RabbitUtil;

import java.util.concurrent.CountDownLatch;

import static com.hhx.rabbitmq.enums.ExchangeEnum.EXCHANGE_DIRECT_001;
import static com.hhx.rabbitmq.enums.QueueEnum.QUEUE_001;

/**
 * 使用自定义消息处理器 应答 ack 与 nack 与 消息重回队列
 * @author hhx
 */
public class Ack {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            try {
                int length = 5;
                CountDownLatch countDownLatch = new CountDownLatch(length);
                EXCHANGE_DIRECT_001.declare();
                QUEUE_001.declare().bind(EXCHANGE_DIRECT_001, "user.save");

                // 发送消息
                for (int i = 0; i < length; i++) {
                    RabbitUtil.publish(EXCHANGE_DIRECT_001,
                            "user.save",
                            "ack message" + i);
                }

                // 自定义一个消费者
                channel.basicConsume(QUEUE_001.getName(),
                        false,
                        new MyConsumer(channel, countDownLatch));
                countDownLatch.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
