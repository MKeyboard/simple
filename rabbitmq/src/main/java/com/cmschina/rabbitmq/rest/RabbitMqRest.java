package com.cmschina.rabbitmq.rest;

import com.cmschina.rabbitmq.service.RabbitMqSendMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbitmq")
@Api("消息队列")
@Slf4j
public class RabbitMqRest {

    @Autowired
    private RabbitMqSendMsgService rabbitMqSendMsgService;

    @ApiOperation("业务队列")
    @GetMapping("/sendMsg")
    public void sendMsg(String msg){
        rabbitMqSendMsgService.sendMsg(msg);
    }

    @ApiOperation("异步业务队列")
    @GetMapping("/sendOrderMsg")
    public void sendOrderMsg(String msg){
        rabbitMqSendMsgService.sendOrderMsg(msg);
    }

}
