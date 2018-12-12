package com.hhx.rabbitmq.enums;

import com.hhx.rabbitmq.util.QueueArgumentBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

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
    QUEUE_001("queue-001", true, false ,false,
            QueueArgumentBuilder.builder()
                .maxLength(10)
                .build()),
    
    QUEUE_002("queue-002", false, false ,false,
            QueueArgumentBuilder.builder()
                .ttl(20 * 1000)
                .deadLetterExchange(ExchangeEnum.EXCHANGE_DEAD.getName())
                .build()),
    
    QUEUE_003("queue-003", true, false ,false,
            QueueArgumentBuilder.builder()
                .maxLength(10)
                .deadLetterExchange(ExchangeEnum.EXCHANGE_DEAD.getName())
                .deadLetterRoutingKey("user.add")
                .build()),
    
    /**
     * 绑定到死信交换机,接受消息
     */
    QUEUE_DEAD("queue-dead", true, false ,false, null),

    ;

    /**
     * 队列名称
     */
    private String name;
    /**
     * 是否可持久化 (重启之后依然存在,若要使队列中消息不丢失，同时也需要将消息声明为持久化)
     */
    private boolean durable;
    /**
     * 是否只能由创建者的连接独占队列, 同一个队列有多个消费者时必须设置为false
     * 设置为true 时  RabbitMQ会自动删除这个队列(connection.close之后)，而不管这个队列是否被声明成持久性的 (durable = true)
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

    /**
     * 如果使用同一套参数进行声明了，就不能再使用其他参数来声明，只能删除该队列重新声明
     * @return
     */
    public QueueEnum declare() {
        try {
            CHANNEL.queueDeclare(name, durable, exclusive, autoDelete, arguments);
        } catch (IOException e) {
            log.info("declare queue `{}` error, message : {}.", name, e.getMessage());
        }
        return this;
    }

    /**
     * 将队列绑定到交换机
     * @param exchange  交换机
     * @param routingKey 路由键
     */
    public QueueEnum bind(ExchangeEnum exchange, String routingKey) {
        try {
            CHANNEL.queueBind(name, exchange.getName(), routingKey);
        } catch (IOException e) {
            log.info("queue `{}` binding exchange `{}` error, message : {}.",
                    name, exchange.getName(), e.getMessage());
        }
        return this;
    }

    /**
     * 删除
     */
    public void delete(){
        try {
            CHANNEL.queueDelete(name);
        } catch (IOException e) {
            // log.info("delete queue `{}` error, message : {}.",name, e.getMessage());
        }
    }
}