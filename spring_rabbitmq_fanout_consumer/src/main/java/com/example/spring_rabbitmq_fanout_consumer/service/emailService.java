package com.example.spring_rabbitmq_fanout_consumer.service;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author lishuai
 * @date 2022/12/5
 */
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "meail.fanout.queue",autoDelete = "false"),
        exchange = @Exchange(value = "spring_boot_fanout",type = ExchangeTypes.FANOUT)
))
@Component
public class emailService {

    //代表此方法是一个消息接收的方法。该不要有返回值
    @RabbitHandler
    public void recviceMessage(String message){
        System.out.println("email--------->"+message);
    }

}
