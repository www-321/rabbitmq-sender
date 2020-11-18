package com.ttlrabbitmq.demo.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public class MessageSend {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String msg) throws JsonProcessingException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(sdf.format(new Date()) + " 发送消息:" +msg);
        Message me = MessageBuilder.withBody(bytes)
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setHeader("x-delay", 3000)
                .build();
        rabbitTemplate.convertAndSend("customDirectExchange", "custom_routing_key", me);

    }

}
