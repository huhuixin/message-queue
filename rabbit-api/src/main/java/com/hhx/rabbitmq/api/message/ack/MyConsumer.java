package com.hhx.rabbitmq.api.message.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author hhx
 */
@Slf4j
public class MyConsumer extends DefaultConsumer {

    private CountDownLatch countDownLatch;

    public MyConsumer(Channel channel, CountDownLatch countDownLatch) {
        super(channel);
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * RabbitMQ 推送消息给 Consumer 时，会附带一个 Delivery Tag，
         * 以便 Consumer 可以在消息确认时告诉 RabbitMQ 到底是哪条消息被确认了
         * 每个信道中，每条消息的 Delivery Tag 从 1 开始递增
         */
        if(RandomUtils.nextBoolean()) {
            getChannel().basicAck(envelope.getDeliveryTag(), false);
            log.info("-----ack : {}", new String(body));
            // 等待消息全部ack之后再结束线程
            countDownLatch.countDown();
        } else {
            // 模拟消息处理失败的情况
            // requeue 是否重回队列 --- 重回队列会把这个消息放回队列的尾部
            getChannel().basicNack(envelope.getDeliveryTag(), false, true);
            log.info("------------nack : {}", new String(body));
        }
    }
}
