package com.lishuai.rabbitmq.fanout;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Properties;
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

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost("/");

        Connection connection = null;
        Channel channel = null;

        try {
            connection = factory.newConnection("消费者");

            channel = connection.createChannel();

            /**
             * @param String queueName
             * @param String exchangeName
             * @param String route-key
             */
            //channel.queueBind("queue2","fanout-exchange1","");

            channel.basicConsume("queue2", true, new DeliverCallback() {
                @Override
                public void handle(String s, Delivery delivery) throws IOException {

                    System.out.println(new String(delivery.getBody(), "utf-8"));
                }
            }, new CancelCallback() {
                @Override
                public void handle(String s) throws IOException {

                }
            });
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
