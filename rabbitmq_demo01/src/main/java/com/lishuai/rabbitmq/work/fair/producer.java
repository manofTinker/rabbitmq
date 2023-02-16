package com.lishuai.rabbitmq.work.fair;

import com.lishuai.rabbitmq.util.RabbitmqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author lishuai
 * @date 2022/12/5
 */
public class producer {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitmqUtil.getConnection();

        Channel channel = connection.createChannel();

        for (int i = 0; i < 20; i++) {

            String message = "fair" + i;

            channel.basicPublish("", "work-2", null, message.getBytes("utf-8"));

        }

        //关闭
        //RabbitmqUtil.closeConnectionAndChannel(channel,connection);

    }
}
