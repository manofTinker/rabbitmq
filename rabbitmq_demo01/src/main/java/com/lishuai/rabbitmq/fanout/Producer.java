package com.lishuai.rabbitmq.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lishuai
 * @date 2022/12/3
 */
public class Producer {
    public static void main(String[] args) {

        String host = "192.168.30.137";
        int port = 5672;
        String username = "admin";
        String password = "admin";

        //1.创建工厂连接
        ConnectionFactory factory = new ConnectionFactory();
        //2.设置连接属性
        factory.setHost(host);
        factory.setPort(port);
        factory.setVirtualHost("/");
        factory.setUsername(username);
        factory.setPassword(password);

        //获取通道和连接
        Connection connection = null;
        Channel channel = null;

        try {
            connection = factory.newConnection("生产者");
            channel = connection.createChannel();

            //定义交换机(创建交换机)
            /**
             *
             * @param String exchange 交换机名称
             * @param String type 模式:
             *                   DIRECT("direct"),
             *                   FANOUT("fanout"),
             *                   TOPIC("topic"),
             *                   HEADERS("headers");
             */
            channel.exchangeDeclare("fanout-exchange1", "fanout");

            channel.queueDeclare("queue2", false, false, false, null);

            String message = "hello,fanout";

            channel.basicPublish("fanout-exchange1", "", null, message.getBytes());

            System.out.println("发送成功");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送失败");
        } finally {
            if (channel != null & channel.isOpen()) {
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
