package com.ttlrabbitmq.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ttlrabbitmq.demo.rabbitmq.MessageSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomController {

    @Autowired
    private MessageSend messageSend;

    @RequestMapping("send")
    public void send() throws JsonProcessingException {
        messageSend.send("wuquan");

    }
}
