package com.cmschina.rabbitmq.service;

public interface RabbitMqSendMsgService {

    void sendMsg(String msg);

    void sendOrderMsg(String msg);
}
