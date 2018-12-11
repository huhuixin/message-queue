package com.hhx.rabbitmq.qos;

import com.hhx.rabbitmq.util.RabbitUtil;

/**
 *
 * @author hhx
 */
public class Produce {

    public static void main(String[] args) {
        RabbitUtil.consumer(channel -> {
            try {
                String exchangeName = "test_qos_exchange";
                String routingKey = "qos.save";
                for(int i =0; i<20; i ++){
                    channel.basicPublish(exchangeName, routingKey, null, ("qos-message" + i).getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
