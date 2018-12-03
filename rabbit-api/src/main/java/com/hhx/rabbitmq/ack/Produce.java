package com.hhx.rabbitmq.ack;

import com.hhx.rabbitmq.Util;
/**
 *
 * @author hhx
 */
public class Produce {

    public static void main(String[] args) {
        Util.consumer(channel -> {
            try {
                String exchangeName = "test_ack_exchange";
                String routingKey = "ack.save";
                for(int i =0; i<5; i ++){
                    channel.basicPublish(exchangeName, routingKey, null, ("ack-message---" + i).getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
