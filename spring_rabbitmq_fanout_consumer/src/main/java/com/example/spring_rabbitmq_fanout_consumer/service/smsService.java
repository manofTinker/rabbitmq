package com.example.spring_rabbitmq_fanout_consumer.service;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author lishuai
 * @date 2022/12/5
 */
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "sms_fanout.queue",autoDelete = "false"),
        exchange = @Exchange(value = "spring_boot_fanout",type = ExchangeTypes.FANOUT)
))
@Component
public class smsService {
    @RabbitHandler
    public void recviceMessage(String message){
        System.out.println("sms--------->"+message);
    }
}
