package com.hhx.rabbitmq.api.message;

import com.hhx.rabbitmq.util.RabbitUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ReturnListener;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static com.hhx.rabbitmq.enums.ExchangeEnum.EXCHANGE_DIRECT_001;
import static com.hhx.rabbitmq.enums.QueueEnum.QUEUE_001;

/**
 * 消息回退处理 mandatory参数
 *
 * @author hhx
 */
@Slf4j
public class MessageReturn {

    public static void main(String[] args) {
        RabbitUtil.useChannel(channel -> {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            EXCHANGE_DIRECT_001.declare();
            QUEUE_001.declare().bind(EXCHANGE_DIRECT_001, "user.save");
            // 接受返回的消息
            channel.addReturnListener(new ReturnListener() {
                @Override
                public void handleReturn(int replyCode, String replyText, String exchange,
                                         String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    log.info("--------- handle  return ----------");
                    log.info("replyCode: {}", replyCode);
                    log.info("replyText: {}", replyText);
                    log.info("exchange: {}", exchange);
                    log.info("routingKey: {}", routingKey);
                    log.info("properties: {}", properties);
                    log.info("body: {}", new String(body));
                    countDownLatch.countDown();
                }
            });
            // 能被正确接受
            RabbitUtil.publish(EXCHANGE_DIRECT_001, "user.save", true, false, null, "user.save message");
            RabbitUtil.publish(EXCHANGE_DIRECT_001, "user.save", false, false, null, "user.save message");
            // 返回给Listener处理
            RabbitUtil.publish(EXCHANGE_DIRECT_001, "user.update", true, false, null, "user.update message");
            // 直接被丢弃
            RabbitUtil.publish(EXCHANGE_DIRECT_001, "user.update", false, false, null, "user.update message");
            try {
                // 主线程等待监听器执行完毕
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
