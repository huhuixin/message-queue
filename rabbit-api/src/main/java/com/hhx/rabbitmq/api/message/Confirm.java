package com.hhx.rabbitmq.api.message;

import com.hhx.rabbitmq.util.RabbitUtil;
import com.rabbitmq.client.ConfirmListener;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static com.hhx.rabbitmq.enums.ExchangeEnum.EXCHANGE_DIRECT_001;
import static com.hhx.rabbitmq.enums.QueueEnum.QUEUE_001;

/**
 * 消息确认模式，发送后通过异步回调机制确认消息是否成功投递交换机
 * @author hhx
 */
@Slf4j
public class Confirm {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            try {
                int length = 10;

                EXCHANGE_DIRECT_001.declare();
                QUEUE_001.declare().bind(EXCHANGE_DIRECT_001, "user.save");

                // 指定消息投递模式: 确认模式
                CountDownLatch countDownLatch = new CountDownLatch(1);
                channel.confirmSelect();
                for (int i = 0; i < length; i++) {
                    RabbitUtil.publish(EXCHANGE_DIRECT_001,
                            "user.save",
                            "user.save message" + i);
                }

                // 开启异步监听 消息是否成功投递到交换机
                channel.addConfirmListener(new ConfirmListener() {
                    // 正常情况下只会触发ack 方法
                    // multiple 为true 代表rabbitmq一次确认了多条消息(比当前tag小的(包含当前)被一次性确认)
                    //          为false 代表只确认当前的 deliveryTag
                    @Override
                    public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                        log.info("Ack --- deliveryTag : {}, multiple : {}", deliveryTag, multiple);
                        if(deliveryTag == length){
                            countDownLatch.countDown();
                        }
                    }

                    // 如果RabbitMQ因为自身原因导致消息丢失, 则会返回nack
                    @Override
                    public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                        log.warn("Nack --- deliveryTag : {}, multiple : {}", deliveryTag, multiple);
                        if(deliveryTag == length){
                            countDownLatch.countDown();
                        }
                    }
                });
                countDownLatch.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
