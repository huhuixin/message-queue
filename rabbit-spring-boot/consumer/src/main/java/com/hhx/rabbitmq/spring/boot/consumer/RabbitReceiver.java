package com.hhx.rabbitmq.spring.boot.consumer;

import com.hhx.rabbitmq.spring.boot.common.RabbitConstant;
import com.hhx.rabbitmq.spring.boot.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * RabbitReceiver
 *
 * @author hhx
 */
@Slf4j
@Component
public class RabbitReceiver {

    @RabbitListener(queues = RabbitConstant.QUEUE_001)
    public void processMessage(Message<User> user) {
        log.info("user add : {}", user.getPayload());
    }

    @RabbitListener(queues = RabbitConstant.QUEUE_002)
    public void processMessage(@Payload User user) {
        log.info("user update : {}", user);
    }
}
