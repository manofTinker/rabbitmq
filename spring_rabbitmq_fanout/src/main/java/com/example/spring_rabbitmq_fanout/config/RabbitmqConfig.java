package com.example.spring_rabbitmq_fanout.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author lishuai
 * @date 2022/12/5
 */
@Configuration
public class RabbitmqConfig {

    //queueDeclare
    @Bean
    public Queue smsQueue(){
        return new org.springframework.amqp.core.Queue("sms_fanout.queue",true);
    }

    @Bean
    public Queue wxQueue(){
        return new Queue("wx.fanout.queue",true);
    }

    @Bean
    public Queue emailQueue(){
        return new Queue("meail.fanout.queue",true);
    }

    //exchangeDeclare
    @Bean
    public DirectExchange exchange(){
        return new DirectExchange("spring_boot_fanout",true,false);
    }

    //queuebind
    @Bean
    public Binding bindingsmsQueue(){
        return BindingBuilder.bind(smsQueue()).to(exchange()).with("");
    }

    @Bean
    public Binding bindingwxQueue(){
        return BindingBuilder.bind(wxQueue()).to(exchange()).with("");
    }

    @Bean
    public Binding bindingemailQueue(){
        return BindingBuilder.bind(emailQueue()).to(exchange()).with("");
    }

}
