package com.lishuai.rabbitmq.work.fair;

import com.lishuai.rabbitmq.util.RabbitmqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author lishuai
 * @date 2022/12/5
 */
public class consumer2 {
    public static void main(String[] args) throws IOException {

        Connection connection = RabbitmqUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare("work-2", false, false, false, null);

        channel.basicQos(1);

        channel.basicConsume("work-2", false, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println(new String(message.getBody(), "utf-8"));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        }, new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        });

    }
}
