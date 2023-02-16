package com.lishuai.rabbitmq.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lishuai
 * @date 2022/12/4
 */

public class Producer {
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
            connection = factory.newConnection("生产者");
            channel = connection.createChannel();

            channel.exchangeDeclare("exchange-direct", "direct");
            channel.basicPublish("exchange-direct", "err", null, "hello,direct".getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
