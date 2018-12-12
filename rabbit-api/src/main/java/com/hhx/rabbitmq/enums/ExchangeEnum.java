package com.hhx.rabbitmq.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

import static com.hhx.rabbitmq.enums.ExchangeTypeEnum.*;
import static com.hhx.rabbitmq.util.RabbitUtil.CHANNEL;

/**
 * 交换机 : 根据指定 RoutingKey 将消息分发到绑定的队列
 *
 * @author hhx
 */
@Slf4j
@Getter
@AllArgsConstructor
public enum ExchangeEnum {
    /**
     *  直联类型 持久化, 不自动删除, 不作为内部使用, 无附加属性
     */
    EXCHANGE_DIRECT_001("exchange-direct-001", DIRECT, true, false, false, null),
    /**
     *  主题类型
     */
    EXCHANGE_TOPIC_001("exchange-topic-001", TOPIC, true, false, false, null),
    /**
     *  分发类型
     */
    EXCHANGE_FANOUT_001("exchange-fanout-001", FANOUT, true, false, false, null),
    /**
     *  死信交换机, 队列中未被正确消费的消息( 超时的,或者超过最大长度限制的消息 )
     *  经过绑定后会被重新路由到这个交换机
     */
    EXCHANGE_DEAD("exchange-dead", TOPIC, true, false, false, null),
    ;

    private String name;
    /**
     * 交换机类型
     */
    private ExchangeTypeEnum type;
    /**
     * 是否可持久化
     */
    private boolean durable;
    /**
     * 是否自动删除 (所有的队列都移除绑定之后自动删除)
     */
    private boolean autoDelete;
    /**
     * 是否是内部专用exchange
     */
    private boolean internal;
    /**
     * 附加属性   value的取值可以是  Number 或  String  或  List
     */
    private Map<String, Object> arguments;

    public ExchangeEnum declare() {
        try {
            CHANNEL.exchangeDeclare(name, type.getType(), durable, autoDelete, internal, arguments);
        } catch (IOException e) {
            log.info("declare exchange `{}` error, message : {}.", name, e.getMessage());
        }
        return this;
    }

    /**
     * 交换器  绑定 交换器
     * 生产者发送消息到 source 交换器中，source 根据路由键找到与其匹配的另一个交换器 destination
     * 并把消息转发到 destination 中, destination 再把消息路由到绑定的队列中
     * @param exchange sourceExchange 从这个交换机上获取消息
     * @param routingKey 路由键
     */
    public ExchangeEnum bind(ExchangeEnum exchange, String routingKey) {
        try {
            CHANNEL.exchangeBind(name, exchange.name, routingKey);
        } catch (IOException e) {
            log.info("source exchange `{}` binding destination exchange `{}` error, message : {}.",
                    name, exchange.name, e.getMessage());
        }
        return this;
    }

    /**
     * 删除
     */
    public void delete(){
        try {
            CHANNEL.exchangeDelete(name);
        } catch (IOException e) {
            // log.info("delete exchange `{}` error, message : {}.",name, e.getMessage());
        }
    }
}
