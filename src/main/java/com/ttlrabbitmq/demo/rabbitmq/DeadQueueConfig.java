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

    @Bean("customDirectExchange")
    public CustomExchange customDirectExchange() {
        Map<String,Object> args=new HashMap<>();
        args.put("x-delay-type","direct");
        return new CustomExchange("test_exchange","x-delayed-message",true,false,args);
    }

    /*
     * 实际队列
     */
    @Bean
    public Queue customQueue() {
        return new Queue("custom_queue_1", true);
    }

    @Bean
    public Binding customBuinding() {
        return BindingBuilder.bind(customQueue()).to(customDirectExchange()).with("custom_routing_key").noargs();
    }


}
