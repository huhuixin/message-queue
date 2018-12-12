package com.hhx.rabbitmq.api.exchange;

import com.hhx.rabbitmq.util.RabbitUtil;

import static com.hhx.rabbitmq.enums.ExchangeEnum.EXCHANGE_TOPIC_001;
import static com.hhx.rabbitmq.enums.QueueEnum.*;

/**
 * @author hhx
 */
public class Topic {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            // 声明交换机
            EXCHANGE_TOPIC_001.declare();

            // 绑定队列
            QUEUE_001.declare().bind(EXCHANGE_TOPIC_001, "user.*");
            QUEUE_002.declare().bind(EXCHANGE_TOPIC_001, "*.save");
            QUEUE_003.declare().bind(EXCHANGE_TOPIC_001, "#");

            RabbitUtil.publish(EXCHANGE_TOPIC_001, "user.save", "user.save message");
            RabbitUtil.publish(EXCHANGE_TOPIC_001, "user.update", "user.update message");
            RabbitUtil.publish(EXCHANGE_TOPIC_001, "order.save", "order.save message");
            RabbitUtil.publish(EXCHANGE_TOPIC_001, "order.update", "order.update message");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
