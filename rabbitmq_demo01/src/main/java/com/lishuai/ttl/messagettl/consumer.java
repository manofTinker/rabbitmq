package com.lishuai.ttl.messagettl;

import com.lishuai.rabbitmq.util.RabbitmqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author lishuai
 * @date 2022/12/6
 */
public class consumer {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.basicConsume("message-ttl", true, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println(new String(message.getBody(), "utf-8"));
            }
        }, new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        });
    }
}
