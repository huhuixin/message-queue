package com.hhx.rabbitmq.api.exchange;

import com.hhx.rabbitmq.util.RabbitUtil;

import static com.hhx.rabbitmq.enums.ExchangeEnum.*;
import static com.hhx.rabbitmq.enums.QueueEnum.*;

/**
 * 交换机绑定
 * @author hhx
 */
public class Bind {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            // 绑定交换机
            EXCHANGE_FANOUT_001.declare()
                    .bind(EXCHANGE_TOPIC_001.declare(), "user.*");

            // 声明队列 绑定队列
            QUEUE_001.declare().bind(EXCHANGE_FANOUT_001, "");

            // 发送消息
            RabbitUtil.publish(EXCHANGE_TOPIC_001, "user.save", "user.save message");
            RabbitUtil.publish(EXCHANGE_TOPIC_001, "order.save", "order.save message");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
