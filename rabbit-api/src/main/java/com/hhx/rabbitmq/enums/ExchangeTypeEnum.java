package com.hhx.rabbitmq.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 交换机类型 : 处理 RoutingKey 的逻辑不同
 *
 * @author hhx
 */
@Getter
@AllArgsConstructor
public enum ExchangeTypeEnum {

    /**
     * 直联 : RoutingKey 与 binding 的字符串完全相等才进行转发
     */
    DIRECT("direct"),
    /**
     * 主题 : RoutingKey与 binding 的字符串进行模糊匹配
     *      `#` 匹配0或多个单词
     *      `.` 匹配一个单词
     * 效率最低
     */
    TOPIC("topic"),
    /**
     * 分发 : 不处理RoutingKey ,直接将消息分发到所有绑定的队列
     * 效率最高
     */
    FANOUT("fanout"),
    /**
     * 标题 : 根据消息附带的消息头分发消息, 基本弃用
     */
    @Deprecated
    HEADERS("headers")
    ;
    private String type;
}
