package com.hhx.rabbitmq.api.message;

import com.hhx.rabbitmq.enums.DeliveryModeEnum;
import com.hhx.rabbitmq.util.RabbitUtil;
import com.rabbitmq.client.AMQP;

import java.util.Map;
import java.util.TreeMap;

import static com.hhx.rabbitmq.enums.ExchangeEnum.EXCHANGE_FANOUT_001;
import static com.hhx.rabbitmq.enums.QueueEnum.QUEUE_001;

/**
 * 消息携带附加属性
 *
 * @author hhx
 */
public class HeadersAndProperties {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            EXCHANGE_FANOUT_001.declare();
            QUEUE_001.declare().bind(EXCHANGE_FANOUT_001, "");
            Map<String, Object> headers = new TreeMap<>();
            headers.put("attr1", "1");
            headers.put("attr2", "2");
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    // 持久化模式 MessageProperties 定义了一些配置模板
                    .deliveryMode(DeliveryModeEnum.PERSISTENT.getMode())
                    // 字符集
                    .contentEncoding("UTF-8")
                    // 过期时间， 过期自动移除
                    .expiration("30000")
                    .headers(headers)
                    .build();
            RabbitUtil.publish(EXCHANGE_FANOUT_001, "", properties, "hello message");
        });
    }
}
