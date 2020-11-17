package com.ttlrabbitmq.demo;

import com.ttlrabbitmq.demo.rabbitmq.MessageSend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private MessageSend messageSend;

    @Test
    void contextLoads() {
    }

    @Test
    public void test2() {

    }

}
