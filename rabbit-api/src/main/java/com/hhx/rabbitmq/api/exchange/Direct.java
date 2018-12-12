package com.hhx.rabbitmq.api.exchange;

import com.hhx.rabbitmq.util.RabbitUtil;

import static com.hhx.rabbitmq.enums.ExchangeEnum.EXCHANGE_DIRECT_001;
import static com.hhx.rabbitmq.enums.QueueEnum.QUEUE_001;

/**
 * @author hhx
 */
public class Direct {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            EXCHANGE_DIRECT_001.declare();

            // 会把发送到 EXCHANGE_DIRECT_001, 且路由键为  user.save  的消息路由到  QUEUE_001
            QUEUE_001.declare().bind(EXCHANGE_DIRECT_001, "user.save");

            // 发送消息指定路由键
            RabbitUtil.publish(EXCHANGE_DIRECT_001, "user.save", "user.save message");
            RabbitUtil.publish(EXCHANGE_DIRECT_001, "user.update", "user.update message");
        });
    }
}
