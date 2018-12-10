package com.hhx.rabbitmq.spring;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hhx
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringTest {

    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test(){
        String test003 = rabbitAdmin.declareQueue(new Queue("test003"));

        rabbitAdmin.declareBinding(
                BindingBuilder
                        //直接创建队列
                        .bind(new Queue("test.topic.queue", false))
                        //直接创建交换机 建立关联关系
                        .to(new TopicExchange("test.topic", false, false))
                        //指定路由Key
                        .with("user.#"));


        rabbitAdmin.declareBinding(
                BindingBuilder
                        .bind(new Queue("test.fanout.queue", false))
                        .to(new FanoutExchange("test.fanout", false, false)));

        //清空队列数据
        rabbitAdmin.purgeQueue("test.topic.queue", false);
    }

    @Test
    public void testSendMessage(){
        log.info("send message ... ");
        //1 创建消息
        MessageProperties messageProperties = new MessageProperties();
        Message message = new Message("Hello RabbitMQ".getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("topic001", "a.a", message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                System.err.println("------添加额外的设置---------");
                message.getMessageProperties().getHeaders().put("desc", "额外修改的信息描述");
                message.getMessageProperties().getHeaders().put("attr", "额外新加的属性");
                return message;
            }
        });
        messageProperties = new MessageProperties();
        messageProperties.setContentType("text/plain");
        message = new Message("mq 消息1234".getBytes(), messageProperties);

        rabbitTemplate.send("topic001", "b.a", message);
        rabbitTemplate.convertAndSend("topic001", "a.a", "send aa!".getBytes());
        rabbitTemplate.convertAndSend("topic001", "a.b", "send ab!");
    }

    @Test
    public void testReceiveMessage(){
        Message test001 = rabbitTemplate.receive("test001");
        System.out.println(new String(test001.getBody()));
    }
}
