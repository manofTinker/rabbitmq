package com.lishuai.rabbitmq.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lishuai
 * @date 2022/12/5
 */
public class RabbitmqUtil {

    private static ConnectionFactory connectionFactory;

    static {
        //创建连接工厂
        connectionFactory = new ConnectionFactory();
        //设置RabbitMQ服务器所在主机ip
        connectionFactory.setHost("192.168.30.137");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");
    }

    public static Connection getConnection() {

        //创建连接对象
        try {
            return connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnectionAndChannel(Channel channel, Connection connection) {
        try {
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

}
