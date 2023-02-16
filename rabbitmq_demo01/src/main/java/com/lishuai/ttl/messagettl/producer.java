package com.lishuai.ttl.messagettl;

import com.lishuai.rabbitmq.util.RabbitmqUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author lishuai
 * @date 2022/12/6
 */
public class producer {
    public static void main(String[] args) throws IOException {

        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();

        HashMap<String, Object> map = new HashMap<>();
        map.put("x",1);
        map.put("y",1);

        channel.queueDeclare("message-ttl",false,false,false,null);

        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder()
                .contentEncoding("utf-8")
                .expiration("9000")
                .messageId("2")
                .priority(1)
                .deliveryMode(1)
                .headers(map).build();

        for (int i = 0; i < 20; i++) {
            String message = "消息ttl" + i;
            channel.basicPublish("","message-ttl",basicProperties,message.getBytes());
            System.out.println("成功！");
        }





    }
}
