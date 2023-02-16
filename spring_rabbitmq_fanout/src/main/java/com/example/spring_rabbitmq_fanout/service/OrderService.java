package com.example.spring_rabbitmq_fanout.service;

import com.example.spring_rabbitmq_fanout.config.RabbitmqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author lishuai
 * @date 2022/12/5
 */
@Component
public class OrderService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    private String EXCHANGE_NAME= "spring_boot_fanout";
    private String ROUTE_KEY ="";

    public void makerder(Long userid){

        String ordernumber = UUID.randomUUID().toString();

        System.out.println("用户："+userid+"当前订单为:"+ordernumber);

        rabbitTemplate.convertAndSend(EXCHANGE_NAME,ROUTE_KEY,ordernumber);
    }

}
