package com.ttlrabbitmq.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttlrabbitmq.demo.rabbitmq.MessageSend;
import com.ttlrabbitmq.demo.rabbitmq.NssaConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ProductController {


    @Autowired
    private MessageSend messageSend;

    @Autowired
    private NssaConfig nssaConfig;


    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("send")
    public void send(String id) throws JsonProcessingException {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("wwwwww-123"+id);
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] ssses = objectMapper.writeValueAsBytes("sss"+id);
        Message message = MessageBuilder.withBody(ssses)
                .setHeader("token","token123")
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        rabbitTemplate.convertAndSend("directExchange", "directKey", message, correlationData);
    }

    @GetMapping("send_error")
    public void sendError() throws JsonProcessingException {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("wwwwww-123");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] ssses = objectMapper.writeValueAsBytes("sss");
        Message message = MessageBuilder.withBody(ssses)
                .setHeader("token","token123")
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        //故意出错，触发returnback回调
        rabbitTemplate.convertAndSend("directExchange", "directKey_error", message, correlationData);
    }


    @GetMapping("send_topic")
    public void send1() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] ssses = objectMapper.writeValueAsBytes("topic.man.queue");
        Message message = MessageBuilder.withBody(ssses)
                .setHeader("token","token123")
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        rabbitTemplate.convertAndSend("topicExchange","topic.man.queue",  message);
    }


    @GetMapping("send_topic2")
    public void send2() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] ssses = objectMapper.writeValueAsBytes("topic.woman.queue");
        Message message = MessageBuilder.withBody(ssses)
                .setHeader("token","token123")
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        rabbitTemplate.convertAndSend("topicExchange","topic.woman.queue",  message);
    }


    @GetMapping("send_fanout")
    public void send3() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] ssses = objectMapper.writeValueAsBytes("send_fanout");
        Message message = MessageBuilder.withBody(ssses)
                .setHeader("token","token123")
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        rabbitTemplate.convertAndSend("fanoutExchange",null,  message);
    }







    @GetMapping("delay")
    public void delay() {
        JSONObject o = new JSONObject();
        o.put("name", "wuquan");
        messageSend.send(o.toJSONString());

    }


    @GetMapping("delay2")
    public void dela2y() {
        JSONObject o = new JSONObject();
        o.put("name", "wuquan");
        messageSend.send2(o.toJSONString());

    }



    @GetMapping("get/{id}")
    public String cons(@PathVariable String id) throws InterruptedException {
       // Thread.sleep(5000);

        return "from 8763" + id;
    }

    @GetMapping("get/name")
    public String name() {
       return nssaConfig.getIp() + nssaConfig.getPort()+"***********";
    }


}
