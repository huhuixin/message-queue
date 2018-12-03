package com.hhx.rabbitmq.dlx;

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
        if(RandomUtils.nextBoolean()) {
            getChannel().basicAck(envelope.getDeliveryTag(), false);
            System.err.println("------ack: " + new String(body));
        } else {
            // 丢进死信队列
            getChannel().basicNack(envelope.getDeliveryTag(), false, false);
            System.err.println("------------nack: " + new String(body));
        }
    }
}
