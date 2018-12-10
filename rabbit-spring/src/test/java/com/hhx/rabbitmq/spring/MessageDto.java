package com.hhx.rabbitmq.spring;

import lombok.Builder;
import lombok.Data;

/**
 * @author hhx
 */
@Data
@Builder
public class MessageDto {
    private Integer id;
    private String message;
}
