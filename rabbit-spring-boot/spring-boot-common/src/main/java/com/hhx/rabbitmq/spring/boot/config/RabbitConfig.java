package com.hhx.rabbitmq.spring.boot.config;

import com.hhx.rabbitmq.spring.boot.common.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * config
 *
 * @author hhx
 */
@Configuration
public class RabbitConfig {

    /**
     * Json 序列化消息
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange exchangeDirect001() {
        return new DirectExchange(RabbitConstant.EXCHANGE_DIRECT_001);
    }

    @Bean
    public Queue queue001() {
        return new Queue(RabbitConstant.QUEUE_001);
    }

    @Bean
    public Queue queue002() {
        return new Queue(RabbitConstant.QUEUE_002);
    }

    @Bean
    public Binding declareBinding001() {
        return BindingBuilder
                .bind(queue001())
                .to(exchangeDirect001())
                .with(RabbitConstant.RK_USER_SAVE);
    }

    @Bean
    public Binding declareBinding002() {
        return BindingBuilder
                .bind(queue002())
                .to(exchangeDirect001())
                .with(RabbitConstant.RK_USER_UPDATE);
    }
}
