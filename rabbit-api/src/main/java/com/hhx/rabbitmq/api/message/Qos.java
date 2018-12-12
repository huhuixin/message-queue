package com.hhx.rabbitmq.api.message;

import com.hhx.rabbitmq.util.RabbitUtil;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;

import static com.hhx.rabbitmq.enums.ExchangeEnum.EXCHANGE_DIRECT_001;
import static com.hhx.rabbitmq.enums.QueueEnum.QUEUE_001;

/**
 * Quality of Service  服务质量
 * 限值一次性处理的消息个数, 避免服务器压力过大
 * @author hhx
 */
@Slf4j
public class Qos {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            try {
                EXCHANGE_DIRECT_001.declare();
                QUEUE_001.declare().bind(EXCHANGE_DIRECT_001, "user.save");

                
                // 先发送10条消息
                for (int i = 0; i < 10; i++) {
                    RabbitUtil.publish(EXCHANGE_DIRECT_001, "user.save", "message " + i);
                }

                /**
                 * prefetchSize：0
                 * prefetchCount：会告诉RabbitMQ不要同时给一个消费者推送多于N个消息，
                 *                即一旦有N个消息还没有ack，则该consumer将block掉，直到有消息ack
                 * global：true\false 是否将上面设置应用于channel，简单点说，就是上面限制是channel级别的还是consumer级别
                 *
                 * prefetchSize 和 global 这两项，rabbitmq没有实现。
                 * 特别注意一点，prefetchCount在autoAck=false的情况下才生效，即在自动应答的情况下这两个值是不生效的。
                 */
                QueueingConsumer consumer = new QueueingConsumer(channel);
                channel.basicQos(0, 3, false);
                channel.basicConsume(QUEUE_001.getName(), false, consumer);
                // 获取消息
                while (true) {
                    QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                    log.info("receive message : {}", new String(delivery.getBody()));
                    // 处理消息需要一定的时间
                    Thread.sleep(3000);
                    // 签收
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    log.info("ack message : {}", new String(delivery.getBody()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
