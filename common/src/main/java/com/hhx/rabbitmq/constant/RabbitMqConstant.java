package com.hhx.rabbitmq.constant;

import com.hhx.rabbitmq.enums.ExchangeEnum;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMq常量
 *
 * @author hhx
 */
@UtilityClass
public class RabbitMqConstant {

    /**
     * 死信队列附加属性, 添加了该属性的队列中的消息如果没有被正确消费,将会被重新路由到指定
     */
    public static final Map<String, Object> DEAD_QUEUE_ARGUMENTS;
    static {
        DEAD_QUEUE_ARGUMENTS = new HashMap<>();
        DEAD_QUEUE_ARGUMENTS.put("x-dead-letter-exchange", ExchangeEnum.EXCHANGE_DEAD.getName());
    }

}
