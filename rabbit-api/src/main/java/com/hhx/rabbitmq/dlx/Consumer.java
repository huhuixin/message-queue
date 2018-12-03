package com.hhx.rabbitmq.dlx;

import com.hhx.rabbitmq.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * 死信队列
 *  1, 被拒绝的消息或返回nack且设置为不重回队列的消息
 *  2, ttl过期的消息
 *  3, 超过队列最大长度的消息
 *
 * 死信队列需要自己声明, 声明之后 将另外的队列绑定到这个死信队列上
 * 如果发生在该队列上的消息发生以上情况,则会进入死信队列
 * agruments      "x-dead-letter-exchange": {队列名称}
 * @author hhx
 */
public class Consumer {

    public static void main(String[] args) {
        Util.consumer(channel -> {
            try {
                // 这就是一个普通的交换机 和 队列 以及路由
                String exchangeName = "test_dlx_exchange";
                String routingKey = "dlx.#";
                String queueName = "test_dlx_queue";

                channel.exchangeDeclare(exchangeName, "topic", true, false, null);

                Map<String, Object> agruments = new HashMap<String, Object>();
                agruments.put("x-dead-letter-exchange", "dlx.exchange");
                //这个agruments属性，要设置到声明队列上
                channel.queueDeclare(queueName, true, false, false, agruments);
                channel.queueBind(queueName, exchangeName, routingKey);

                //要进行死信队列的声明:
                channel.exchangeDeclare("dlx.exchange", "topic", true, false, null);
                channel.queueDeclare("dlx.queue", true, false, false, null);
                channel.queueBind("dlx.queue", "dlx.exchange", "#");

                channel.basicConsume(queueName, true, new MyConsumer(channel));
                Thread.sleep(1000000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
