package com.hhx.rabbitmq.util;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 队列附加属性
 *
 * @author hhx
 */
public class QueueArgumentBuilder {

    @Getter
    private Map<String, Object> arguments;

    private QueueArgumentBuilder() {
        this.arguments = new HashMap<>(2);
    }

    public static QueueArgumentBuilder builder(){
        return new QueueArgumentBuilder();
    }

    public Map<String, Object> build(){
        return arguments;
    }

    /**
     * time to live 生存时间, 统一定义队列中消息的过期时间
     * @param ttl 保存时间 单位毫秒
     * @return
     * Features TTL
     */
    public QueueArgumentBuilder ttl(long ttl){
        arguments.put("x-message-ttl", ttl);
        return this;
    }

    /**
     * 队列在指定的时间之内没有被访问, 就会被删除
     * @param expires 闲置时间 单位毫秒
     * @return
     * Features Exp
     */
    public QueueArgumentBuilder expires(long expires){
        arguments.put("x-expires", expires);
        return this;
    }

    /**
     * 限定队列的消息的最大值长度，超过指定长度将会把最早的几条删除掉
     * @param maxLength
     * @return
     * Features Lim
     */
    public QueueArgumentBuilder maxLength(long maxLength){
        arguments.put("x-max-length", maxLength);
        return this;
    }

    /**
     * 限定队列最大占用的空间大小， 一般受限于内存、磁盘的大小
     * @param maxLengthBytes
     * @return
     * Features Lim B
     */
    public QueueArgumentBuilder maxLengthBytes(long maxLengthBytes){
        arguments.put("x-max-length-bytes", maxLengthBytes);
        return this;
    }

    /**
     * 队列消息长度大于最大长度、或者过期的等，将从队列中删除的消息推送到指定的交换机中去而不是丢弃掉
     * @param deadLetterExchange 死信队列
     * @return
     * Features DLX
     */
    public QueueArgumentBuilder deadLetterExchange(String deadLetterExchange){
        arguments.put("x-dead-letter-exchange", deadLetterExchange);
        return this;
    }

    /**
     * 将删除的消息推送到指定交换机的指定路由键的队列中去
     * @param deadLetterRoutingKey 路由到死信队列时指定路由键
     * @return
     * Feature DLK
     */
    public QueueArgumentBuilder deadLetterRoutingKey(String deadLetterRoutingKey){
        arguments.put("x-dead-letter-routing-key", deadLetterRoutingKey);
        return this;
    }

    /**
     * 优先级队列 发布消息的时候指定该消息的优先级， 优先级更高（数值更大的）的消息先被消费
     * @param maxPriority 最大优先级
     * @return
     * Feature Pri
     */
    public QueueArgumentBuilder maxPriority(long maxPriority){
        arguments.put("x-max-priority", maxPriority);
        return this;
    }
}
