package com.hhx.rabbitmq.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;

/**
 * @author hhx
 */
public class MyConsumer extends DefaultConsumer {

    public MyConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.err.println("body: " + new String(body));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(RandomUtils.nextBoolean()) {
            getChannel().basicAck(envelope.getDeliveryTag(), false);
            System.err.println("------ack: " + new String(body));
        } else {
            // 模拟消息处理失败的情况
            // 第三个参数代表 是否重回队列 --- 重回队列会把这个消息放回队列的尾部
            getChannel().basicNack(envelope.getDeliveryTag(), false, true);
            System.err.println("------------nack: " + new String(body));
        }
    }
}
