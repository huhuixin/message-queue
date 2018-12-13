package com.hhx.rabbitmq.spring.boot.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 订单
 *
 * @author hhx
 */
@Data
@Accessors(chain = true)
public class User implements Serializable {
    private Integer id;
    private String name;
}
