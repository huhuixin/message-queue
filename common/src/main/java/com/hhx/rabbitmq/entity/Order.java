package com.hhx.rabbitmq.entity;

import lombok.Builder;
import lombok.Data;

/**
 * 订单
 *
 * @author hhx
 */
@Data
@Builder
public class Order {
    private String orderNumber;
    private Double price;
    private String username;
}
