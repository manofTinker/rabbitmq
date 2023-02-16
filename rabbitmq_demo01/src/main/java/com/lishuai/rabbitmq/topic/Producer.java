package com.lishuai.rabbitmq.topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lishuai
 * @date 2022/12/5
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

            channel.exchangeDeclare("exchange-topic", "topic");

            channel.basicPublish("exchange-topic", "success.log", null, "success.log".getBytes());

            channel.basicPublish("exchange-topic", "err.log", null, "err.log".getBytes());

            channel.basicPublish("exchange-topic", "a.b.log", null, "a.b.log".getBytes());

            System.out.println("发送成功");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("发送失败");
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

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
