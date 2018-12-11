package com.hhx.rabbitmq.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static com.hhx.rabbitmq.constant.RabbitMqConstant.DEAD_QUEUE_ARGUMENTS;
import static com.hhx.rabbitmq.util.RabbitUtil.CHANNEL;

/**
 * 队列 : 存储消息的容器
 *
 * @author hhx
 */
@Slf4j
@Getter
@AllArgsConstructor
public enum QueueEnum {
    /**
     * 队列枚举
     */
    QUEUE_001("queue-001", true, false ,false, null),
    
    QUEUE_002("queue-002", false, false ,false, DEAD_QUEUE_ARGUMENTS),
    
    QUEUE_003("queue-003", true, true ,false, DEAD_QUEUE_ARGUMENTS),
    
    /**
     * 绑定到死信交换机,接受消息
     */
    QUEUE_DEAD("queue-dead", true, true ,false, null),

    ;

    /**
     * 队列名称
     */
    private String name;
    /**
     * 是否可持久化(重启之后依然存在,若要使队列中消息不丢失，同时也需要将消息声明为持久化)
     */
    private boolean durable;
    /**
     * 是否只能由创建者独占队列, 同一个队列有多个消费者时必须设置为false
     */
    private boolean exclusive;
    /**
     * 是否自动删除(最后一个消费者失去连接的时候自动删除队列)
     */
    private boolean autoDelete;
    /**
     * 队列的附加属性   value的取值可以是  Number 或  String  或  List
     */
    private Map<String, Object> arguments;

    public void declare() {
        try {
            // 先移除再创建, 避免属性冲突
            CHANNEL.queueDelete(name);
            CHANNEL.queueDeclare(name, durable, exclusive, autoDelete, arguments);
        } catch (IOException e) {
            log.info("declare queue `{}` error, message : {}.", name, e.getMessage());
        }
    }

    /**
     * 将队列绑定到交换机
     * @param exchange  交换机
     * @param routingKey 路由键
     */
    public void bindExchange(ExchangeEnum exchange, String routingKey) {
        try {
            CHANNEL.queueBind(name, exchange.getName(), routingKey);
        } catch (IOException e) {
            log.info("binding queue `{}` from exchange `{}` error, message : {}.",
                    name, exchange.getName(), e.getMessage());
        }
    }

    /**
     * 清空队列
     */
    public void clear(){
        try {
            CHANNEL.queuePurge(name);
        } catch (IOException e) {
            log.info("clear queue `{}` error, message : {}.", name, e.getMessage());
        }
    }

    /**
     * 清空所有队列
     */
    public static void clearAll(){
        Arrays.stream(QueueEnum.values()).forEach(QueueEnum::clear);
    }
}