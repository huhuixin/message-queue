package com.hhx.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

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
}
