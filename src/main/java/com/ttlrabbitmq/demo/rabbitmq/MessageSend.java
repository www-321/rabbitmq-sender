package com.ttlrabbitmq.demo.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public class MessageSend {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        rabbitTemplate.convertAndSend("dead_exchange_1","dead_routing_key", msg,(message)->{
            message.getMessageProperties().setDelay( 10000);//设置延迟时间
            return message;
        });
        System.out.println(sdf.format(new Date()));

    }

}
