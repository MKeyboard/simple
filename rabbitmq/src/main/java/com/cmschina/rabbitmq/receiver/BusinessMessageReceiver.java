package com.cmschina.rabbitmq.receiver;

import com.cmschina.rabbitmq.config.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class BusinessMessageReceiver {

    /**
     * 业务A队列消费代码
     */
    @RabbitListener(queues = RabbitMqConfig.BUSINESS_QUEUEA_NAME)
    public void receiverA(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("收到业务消息A：{}",msg);
        boolean ack = true;
        Exception exception = null;
        try{
            if (msg.contains("deadletter")){
                throw new RuntimeException("dead letter exception");
            }
        }catch (Exception e){
            ack = false;
            exception = e;
        }
        if (!ack){
            log.error("消息消费发生异常，error msg:{}", exception.getMessage(), exception);
            // 将信息重新放回到队列中
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        }else{
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }
    }

    /**
     * 业务B队列消费代码
     */
    @RabbitListener(queues = RabbitMqConfig.BUSINESS_QUEUEB_NAME)
    public void receiverB(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("收到业务消息B：{}",msg);
        // 手动消息确认步骤 配合acknowledge-mode: manual 模式
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }


}
