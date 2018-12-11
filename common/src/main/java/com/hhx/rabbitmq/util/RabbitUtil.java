package com.hhx.rabbitmq.util;

import com.hhx.rabbitmq.enums.ExchangeEnum;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author hhx
 */
@Slf4j
public class RabbitUtil {

    public static Channel CHANNEL = null;

    /**
     * 简化代码
     * @param channelConsumer 使用channel完成的操作
     */
    public static void useChannel(Consumer<Channel> channelConsumer) {
        try{
            Connection connection = null;
            Channel channel = null;
            try{
                // 1 创建连接工厂
                log.info("connection to rabbitmq server");
                ConnectionFactory connectionFactory = new ConnectionFactory();
                connectionFactory.setHost("rabbitmq.huhuixin.com");
                connectionFactory.setPort(5672);
                connectionFactory.setVirtualHost("/");
                // 2 建立连接
                connection = connectionFactory.newConnection();
                // 3 建立通道
                CHANNEL = connection.createChannel();
                // 4 使用 channel进行 创建,绑定,发布,接受等操作
                channelConsumer.accept(CHANNEL);
            }finally {
                // 5 关闭连接
                if(CHANNEL != null){
                    CHANNEL.close();
                }
                if (connection != null){
                    connection.close();
                }
            }
        }catch (Exception e){
            log.error("message: {}", e.getMessage());
        }
    }

    /**
     * 发布消息
     * @param exchange 发布到哪个交换机
     * @param routingKey 不同类型的交换机根据 对 routingKey 有不同的路由策略
     * @param mandatory 如果 exchange 根据自身类型和消息 routingKey 无法找到一个符合条件的 queue
     *      设置为true时, 将消息返回给生产者
     *      设置为false时, 直接将消息丢掉
     * @param immediate RabbitMQ3.0以后的版本里，去掉了immediate参数支持
     * @param props 消息属性字段，比如消息头部信息等等
     * @param body 消息主体
     */
    public static void publish(ExchangeEnum exchange,
                               String routingKey,
                               boolean mandatory,
                               boolean immediate,
                               AMQP.BasicProperties props,
                               String body) {
        try {
            CHANNEL.basicPublish(
                    exchange == null ? "" : exchange.getName() ,
                    routingKey,
                    mandatory,
                    immediate,
                    props,
                    body.getBytes());
        } catch (IOException e) {
            log.error("message: {}", e.getMessage());
        }
    }

    public static void publish(ExchangeEnum exchange,
                               String routingKey,
                               String body) {
        publish(exchange, routingKey, false, false, null, body);
    }

    public static void publish(ExchangeEnum exchange,
                               String routingKey,
                               AMQP.BasicProperties props,
                               String body) {
        publish(exchange, routingKey, false, false, props, body);
    }
}
