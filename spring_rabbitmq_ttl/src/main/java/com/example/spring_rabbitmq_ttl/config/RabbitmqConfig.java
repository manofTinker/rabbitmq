package com.example.spring_rabbitmq_ttl.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author lishuai
 * @date 2022/12/6
 */
@Configuration
public class RabbitmqConfig {

    //定义队列
    @Bean("spring-queueA")
    public Queue QueueA(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange","spring.dead.exchange");
        map.put("x-dead-letter-routing-key","spring.dead.routingkeyA");
        map.put("x-message-ttl",2000);
        //return new Queue("",false,false,false,map);
        return new Queue("Spring-QueueA",true,false,false,map);
    }

    //定义死信队列
    @Bean("deadqueueA")
    public Queue DeadQueueA(){
        return new Queue("spring.dead.QueueA",true);
    }

    @Bean("deadexchange")
    public DirectExchange Deadexchange(){
        return new DirectExchange("spring.dead.exchange",true,false);
    }

    @Bean("exchange")
    public DirectExchange exchange(){
        return new DirectExchange("spring.exchange",true,false);
    }

    //绑定死信队列和key
    @Bean
    public Binding BindDeadQueueA(@Qualifier("deadqueueA") Queue queue,@Qualifier("deadexchange") DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with("spring.dead.routingkeyA");
    }

    @Bean
    public Binding BindQueueA(@Qualifier("spring-queueA") Queue queue,@Qualifier("exchange") DirectExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange).with("spring-routing-key");
    }










}
