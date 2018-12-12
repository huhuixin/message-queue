package com.hhx.rabbitmq.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息持久化策略
 *
 * @author hhx
 */
@Getter
@AllArgsConstructor
public enum DeliveryModeEnum {
    /**
     * 不持久化
     */
    NON_PERSISTENT(1),
    /**
     * 持久化
     */
    PERSISTENT(2);
    private Integer mode;
}
