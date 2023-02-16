package com.example.spring_rabbitmq_ttl;

import com.example.spring_rabbitmq_ttl.config.RabbitmqConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SpringRabbitmqTtlApplicationTests {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
        rabbitTemplate.convertAndSend("spring.exchange","spring-routing-key","1");
    }

}
