package com.ttlrabbitmq.demo.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 把一个设有过期时间的消息投入一个临时队列（该队列没有消费者）等待过期，也没有设置requeue（重新入对），就变成死信
 * 死信会自动的被投入到配置好的死信队列中去，然后发送给配置好的消费者消费
 */

@Configuration
public class DelayQueueConfiguration {

    public static String DELAY_EXCHANGE = "delay_exchange";
    public static String DELAY_EXCHANGE_KEY = "delay_routing_key";
    /*
    1，创建临时队列和临时交换机，临时队列设置超时时间，过期后投放到死信交换机
    2，将消息通过临时交换机绑定到临时队列
    3，死信队列（即业务处理队列）和死信交换机：死信交换机收到第1步的消息，并把消息投档到死信队列
    4，
    消息投递时直接投递到临时交换机和队列

     */

    /*
    临时队列
     */
    @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable("delay_queue")
                //过期之后，消息投放的交换机
                .withArgument("x-dead-letter-exchange", DELAY_EXCHANGE)
                //指定的key
                .withArgument("x-dead-letter-routing-key", DELAY_EXCHANGE_KEY)
                .withArgument("x-message-ttl", 10000)
                .build();
    }

    @Bean
    public DirectExchange delayTtlExchange() {
        //临时队列交换机，讲消息直接唐此交换机
        return new DirectExchange("delay_ttl_exchange", true, false);
    }

    @Bean
    public Binding delayTtlBinding() {
        //临时队列和交换机绑定
        return BindingBuilder.bind(delayQueue()).to(delayTtlExchange()).with("delay_ttl_key");
    }


    /*
    业务队列（死信队列）
     */
    @Bean
    public Queue deadQueue() {
        return new Queue("dead_process_queue", true);
    }


    /*
    死信交换机：
    临时队列消息过期，会被投放到这个交换机
     */
    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE, true, false);
    }

    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(deadQueue()).to(delayExchange()).with(DELAY_EXCHANGE_KEY);
    }

}
