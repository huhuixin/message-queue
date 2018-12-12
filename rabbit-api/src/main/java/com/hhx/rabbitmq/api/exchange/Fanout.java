package com.hhx.rabbitmq.api.exchange;

import com.hhx.rabbitmq.util.RabbitUtil;

import static com.hhx.rabbitmq.enums.ExchangeEnum.EXCHANGE_FANOUT_001;
import static com.hhx.rabbitmq.enums.QueueEnum.QUEUE_001;
import static com.hhx.rabbitmq.enums.QueueEnum.QUEUE_002;

/**
 * @author hhx
 */
public class Fanout {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            EXCHANGE_FANOUT_001.declare();

            // FANOUT 交换机会忽略路由键
            QUEUE_001.declare().bind(EXCHANGE_FANOUT_001, "");
            QUEUE_002.declare().bind(EXCHANGE_FANOUT_001, "");

            RabbitUtil.publish(EXCHANGE_FANOUT_001, "", "hello fanout 1");
            RabbitUtil.publish(EXCHANGE_FANOUT_001, "test", "hello fanout 2");
        });
    }
}
