package com.ttlrabbitmq.demo.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 延迟队列，要安装延迟插件
 */
@Configuration
public class DeadQueueConfig {

    @Bean
    public Exchange customDirectExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type","direct");
        return new CustomExchange("dead_exchange_1","x-delayed-message",true,false,args);
    }

    /*
     * 实际队列
     */
    @Bean
    public Queue customQueue() {
        return QueueBuilder.durable("dead_queue_1").build();
    }

    @Bean
    public Binding customBuinding() {
        return BindingBuilder.bind(customQueue()).to(customDirectExchange()).with("dead_routing_key").noargs();
    }


}
