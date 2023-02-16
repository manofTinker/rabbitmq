package com.lishuai.rabbitmq.work.round;

import com.lishuai.rabbitmq.util.RabbitmqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author lishuai
 * @date 2022/12/5
 */
public class consumer1 {

    public static void main(String[] args) throws IOException {

        Connection connection = RabbitmqUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare("work-1", false, false, false, null);

        //同一时刻，服务器只会推送一条消息给消费者
        channel.basicQos(1);

        channel.basicConsume("work-1", true, new DeliverCallback() {
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
