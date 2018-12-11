package com.hhx.rabbitmq.quickstart;

import com.hhx.rabbitmq.enums.QueueEnum;
import com.hhx.rabbitmq.util.RabbitUtil;
import com.rabbitmq.client.*;

/**
 * 消费者
 * @author hhx
 */
public class Consumer {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            try {
                // 队列的消费者
                QueueingConsumer consumer = new QueueingConsumer(channel);
                // 声明队列
                QueueEnum.QUEUE_001.declare();
                // 将消费者绑定到队列上   autoAck 自动应答
                channel.basicConsume(QueueEnum.QUEUE_001.getName(), true, consumer);
                // 获取消息
                for (int i = 0; i < 5; i++) {
                    System.out.println("consumer: " + new String(consumer.nextDelivery().getBody()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
