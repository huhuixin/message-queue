package com.hhx.rabbitmq.api.message;

import com.hhx.rabbitmq.util.RabbitUtil;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.hhx.rabbitmq.enums.ExchangeEnum.*;
import static com.hhx.rabbitmq.enums.QueueEnum.*;

/**
 * 死信队列
 * 当消息在一个队列中变成死信(dead message)之后，
 * 它能被重新publish到另一个exchange，这个exchange就是dlx
 * @author hhx
 */
@Slf4j
public class DeadLetter {
    /**
     *  1.消息被拒绝(basic.reject / basic.nack)并且reQueue=false
     *
     *  2.消息TTL过期
     *
     *  3.队列达到最大长度了
     */
    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            // 声明一个普通的交换机作为 死信交换机
            EXCHANGE_DEAD.declare();

            // 声明一个队列接受死信交换机的消息  (可以根据路由KEY声明多个队列, 这里为了方便起见,接收所有的消息)
            QUEUE_DEAD.declare().bind(EXCHANGE_DEAD, "#");

            // 另一个交换机用于接受消息
            EXCHANGE_DIRECT_001.declare();

            // 声明一个普通的队列, 消息即使成为死信也不会被路由到
            // 添加属性 maxLength 但是超过最大数量限制之后不作任何处理
            QUEUE_001.declare().bind(EXCHANGE_DIRECT_001, "user.delete");
            // ttl 超时过期
            // deadLetterExchange  设置为死信队列,指明死信交换机
            QUEUE_002.declare().bind(EXCHANGE_DIRECT_001, "user.update");
            // maxLength
            // deadLetterExchange
            // deadLetterRoutingKey  成为死信之后以指定的路由Key 转发到死信交换机
            QUEUE_003.declare().bind(EXCHANGE_DIRECT_001, "user.save");


            // 发送消息
            for (int i = 0; i < 15; i++) {
                // QUEUE_001
                RabbitUtil.publish(EXCHANGE_DIRECT_001, "user.delete", "delete user " + i);
                // QUEUE_002
                RabbitUtil.publish(EXCHANGE_DIRECT_001, "user.update", "update user " + i);
                // QUEUE_003
                RabbitUtil.publish(EXCHANGE_DIRECT_001, "user.save", "save user " + i);
            }

            try {
                // 拒收 QUEUE_003 的消息
                QueueingConsumer consumer = new QueueingConsumer(channel);
                channel.basicConsume(QUEUE_003.getName(), false, consumer);
                // 获取消息
                while (true) {
                    QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                    Thread.sleep(3000);
                    // 拒收 且不重回队列
                    channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
                    log.info("nack message : {}", new String(delivery.getBody()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
