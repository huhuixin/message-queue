package com.hhx.rabbitmq.quickstart;

import com.hhx.rabbitmq.enums.QueueEnum;
import com.hhx.rabbitmq.util.RabbitUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * 生产者
 * @author hhx
 */
@Slf4j
public class Producer {

    public static void main(String[] args) {
        RabbitUtil.useChannel(false, channel ->{
            for (int i = 0; i < 5; i++) {
                // 交换机为空, 会吧消息路由到 名称与路由键完全相同的队列
                RabbitUtil.publish(null, QueueEnum.QUEUE_001.getName(), "Hello RabbitMQ" + i);
            }
        });
    }
}
