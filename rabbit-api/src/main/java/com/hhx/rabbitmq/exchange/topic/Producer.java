package com.hhx.rabbitmq.exchange.topic;

import com.hhx.rabbitmq.util.RabbitUtil;

import static com.hhx.rabbitmq.enums.ExchangeEnum.EXCHANGE_TOPIC_001;
import static com.hhx.rabbitmq.enums.QueueEnum.*;

/**
 * @author hhx
 */
public class Producer {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            // 声明交换机
            EXCHANGE_TOPIC_001.declare();
            // 声明队列
            QUEUE_001.declare();
            QUEUE_002.declare();
            QUEUE_003.declare();
            // 绑定队列
            EXCHANGE_TOPIC_001.bindQueue(QUEUE_001, "user.*");
            EXCHANGE_TOPIC_001.bindQueue(QUEUE_002, "*.save");
            EXCHANGE_TOPIC_001.bindQueue(QUEUE_003, "#");

            RabbitUtil.publish(EXCHANGE_TOPIC_001, "user.save", "user.save");
            RabbitUtil.publish(EXCHANGE_TOPIC_001, "user.update", "user.update");
            RabbitUtil.publish(EXCHANGE_TOPIC_001, "order.save", "order.save");
            RabbitUtil.publish(EXCHANGE_TOPIC_001, "order.update", "order.update");
        });
    }
}
