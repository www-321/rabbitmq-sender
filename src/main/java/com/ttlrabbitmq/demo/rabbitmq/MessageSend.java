package com.ttlrabbitmq.demo.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
            message.getMessageProperties().setDelay( 30000);//设置延迟时间
            return message;
        });
        System.out.println(sdf.format(new Date()));

    }

}
