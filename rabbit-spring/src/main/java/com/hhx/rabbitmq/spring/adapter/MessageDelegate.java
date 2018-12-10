package com.hhx.rabbitmq.spring.adapter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hhx
 */
@Slf4j
public class MessageDelegate {

    /**
     * 方法名默认为 handleMessage ,可以自定义但没有必要
     * 接受的消息类型  content_type : text/plain
     */
    public void handleMessage(byte[] body){
        log.info("byte[] message : {}", new String(body));
    }

    /**
     * 接受的消息类型  content_type : application/octet-stream
     */
    public void handleMessage(String body){
        log.info("String message : {}", body);
    }
}
