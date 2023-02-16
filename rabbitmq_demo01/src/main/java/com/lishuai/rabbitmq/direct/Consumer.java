package com.lishuai.rabbitmq.direct;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lishuai
 * @date 2022/12/4
 */
public class Consumer {

    public static void main(String[] args) {
        String host = "192.168.30.137";
        int port = 5672;
        String username = "admin";
        String password = "admin";
        String Virtual = "/";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(Virtual);

        Connection connection = null;
        Channel channel = null;

        try {
            connection = factory.newConnection("消费者");
            channel = connection.createChannel();

            channel.exchangeDeclare("exchange-direct", "direct");

            channel.queueDeclare("queue_direct", false, false, false, null);

            channel.queueBind("queue_direct", "exchange-direct", "err");

            channel.basicConsume("queue_direct", true, new DeliverCallback() {
                @Override
                public void handle(String consumerTag, Delivery message) throws IOException {
                    System.out.println(new String(message.getBody(), "utf-8"));
                }
            }, new CancelCallback() {
                @Override
                public void handle(String consumerTag) throws IOException {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
