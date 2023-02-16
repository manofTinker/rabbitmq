package com.lishuai.rabbitmq.topic;

import com.rabbitmq.client.*;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author lishuai
 * @date 2022/12/5
 */
public class Consumer {
    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.30.137");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");

        Connection connection = null;
        Channel channel = null;

        try {
            connection = factory.newConnection();

            channel = connection.createChannel();

            channel.queueDeclare("queue_topic1", false, false, false, null);
            channel.queueBind("queue_topic1", "exchange-topic", "#.log");

            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    System.out.println(new String(body, "utf-8"));

                    System.out.println();
                }
            };

            channel.basicConsume("queue_topic1", true, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
