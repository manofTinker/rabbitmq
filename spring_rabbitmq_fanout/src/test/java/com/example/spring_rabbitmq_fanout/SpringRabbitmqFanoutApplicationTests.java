package com.example.spring_rabbitmq_fanout;

import com.example.spring_rabbitmq_fanout.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringRabbitmqFanoutApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() throws InterruptedException {
        for (int i = 0 ; i < 20 ; i++){
            Thread.sleep(1000);

            Long id = 100l +i;

            orderService.makerder(id);
        }
    }
}
