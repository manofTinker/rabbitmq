package com.lishuai.ttl.queueTTL;

import com.lishuai.rabbitmq.util.RabbitmqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author lishuai
 * @date 2022/12/6
 */
public class queueTTLConsumer {
    public static void main(String[] args) throws IOException {

        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.basicConsume("queue-ttl", true, new DeliverCallback() {
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
