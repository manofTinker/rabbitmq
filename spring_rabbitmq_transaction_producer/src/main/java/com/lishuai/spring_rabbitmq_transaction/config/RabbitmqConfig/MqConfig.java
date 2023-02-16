package com.lishuai.spring_rabbitmq_transaction.config.RabbitmqConfig;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lishuai
 * @date 2022/12/7
 */
@Configuration
public class MqConfig {


    @Value("spring.rabbitmq.host")
    private String host;
    @Value("spring.rabbitmq.port")
    private int port;
    @Value("spring.rabbitmq.username")
    private String username;
    @Value("spring.rabbitmq.password")
    private String password;

    @Bean
    public CachingConnectionFactory Connection(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();

        //设置连接基础信息
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost("/");

        return cachingConnectionFactory;

    }

}
