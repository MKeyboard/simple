package com.cmschina.rabbitmq.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.cmschina.rabbitmq.config.RabbitMqConfig;
import com.cmschina.rabbitmq.service.RabbitMqSendMsgService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSendMsgServiceImpl implements RabbitMqSendMsgService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMsg(String msg) {
        rabbitTemplate.convertSendAndReceive(RabbitMqConfig.BUSINESS_EXCHANGE_NAME,"",msg);
    }

    @Override
    public void sendOrderMsg(String msg) {
        ThreadUtil.execute(new SendMsg(msg));
    }

    /**
     * 构建异步类
     */
    class SendMsg implements Runnable{

        private String msg;

        public SendMsg(String msg){
            this.msg = msg;
        }
        @Override
        public void run() {
            rabbitTemplate.convertSendAndReceive(RabbitMqConfig.BUSINESS_EXCHANGE_NAME,"",msg);
        }
    }
}
