package com.hhx.rabbitmq.quickstart;

import com.hhx.rabbitmq.enums.QueueEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

import static com.hhx.rabbitmq.util.RabbitUtil.*;

/**
 * 生产者
 * @author hhx
 */
@Slf4j
public class Producer {

    public static void main(String[] args) {
        useChannel(channel ->{
            for (int i = 0; i < 5; i++) {
                // 交换机为空, 会吧消息路由到 名称与路由键完全相同的队列
                publish(null, QueueEnum.QUEUE_001.getName(), "Hello RabbitMQ" + i);
            }
        });
    }
}
