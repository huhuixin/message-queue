package com.hhx.rabbitmq.dlx;

import com.hhx.rabbitmq.Util;

/**
 * 应答 ack 与 nack 与 消息重回队列
 * @author hhx
 */
public class DlxConsumer {

    public static void main(String[] args) {
        Util.consumer(channel -> {
            try {
                channel.basicConsume("dlx.queue", true, new MyDlxConsumer(channel));
                Thread.sleep(1000000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
