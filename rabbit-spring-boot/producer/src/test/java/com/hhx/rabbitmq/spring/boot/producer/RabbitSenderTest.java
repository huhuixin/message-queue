package com.hhx.rabbitmq.spring.boot.producer;

import com.hhx.rabbitmq.spring.boot.common.RabbitConstant;
import com.hhx.rabbitmq.spring.boot.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitSenderTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendUser() {
        // 增加用户
        User user = new User().setId(1).setName("小明");
        rabbitTemplate.convertAndSend(RabbitConstant.EXCHANGE_DIRECT_001,
                RabbitConstant.RK_USER_SAVE, user);
        // 修改用户
        user.setName("小黑");
        rabbitTemplate.convertAndSend(RabbitConstant.EXCHANGE_DIRECT_001,
                RabbitConstant.RK_USER_UPDATE, user);
    }
}