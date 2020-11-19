package com.ttlrabbitmq.demo.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ttlrabbitmq.demo.rabbitmq.DelayQueueConfiguration.DELAY_EXCHANGE;
import static com.ttlrabbitmq.demo.rabbitmq.DelayQueueConfiguration.DELAY_EXCHANGE_KEY;

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


    public void send2(String msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Message message = MessageBuilder.withBody(msg.getBytes())
                .setHeader("token", "token-value")
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        rabbitTemplate.convertAndSend("delay_ttl_exchange","delay_ttl_key", message);

        System.out.println(sdf.format(new Date()) +"发送");

    }



}
