package com.hhx.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author hhx
 */
public class Util {

    public static void consumer(Consumer<Channel> channelConsumer) {
        try{
            Connection connection = null;
            Channel channel = null;
            try{
                // 1 创建连接工厂
                ConnectionFactory connectionFactory = new ConnectionFactory();
                connectionFactory.setHost("rabbitmq.huhuixin.com");
                connectionFactory.setPort(5672);
                connectionFactory.setVirtualHost("/");

                // 2 建立连接
                connection = connectionFactory.newConnection();

                // 3 建立通道
                channel = connection.createChannel();

                // 使用 channel
                channelConsumer.accept(channel);
            }finally {
                // 5 关闭连接
                if(channel != null){
                    channel.close();
                }
                if (connection != null){
                    connection.close();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param channel 信道
     * @param exchangeName  创建 exchange
     * @param exchangeType  exchange 类型
     *                      direct  根据routingKey完全匹配
     *                      topic   根据routingKey模糊匹配
     *                      fanout  routingKey不起作用，直接路由到绑定的所有队列
     * @param queueName     queue
     * @param routingKey    路由键
     * @return 消费者
     * @throws IOException e
     */
    public static QueueingConsumer createQueueAndBind(Channel channel, String exchangeName, String exchangeType, String queueName, String routingKey) throws IOException {
        //表示声明了一个交换机
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
        //表示声明了一个队列
        channel.queueDeclare(queueName, false, false, false, null);
        //建立一个绑定关系   发送到 ${exchangeName} 且 routingKey为 ${routingKey} 的消息会被路由到 ${queueName}
        channel.queueBind(queueName, exchangeName, routingKey);
        //durable 是否持久化消息
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //参数：队列名称、是否自动ACK(签收)、Consumer
        channel.basicConsume(queueName, true, consumer);
        return consumer;
    }

}
