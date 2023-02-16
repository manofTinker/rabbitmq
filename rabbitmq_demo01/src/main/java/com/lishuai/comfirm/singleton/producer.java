package com.lishuai.comfirm.singleton;

import com.lishuai.rabbitmq.util.RabbitmqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lishuai
 * @date 2022/12/7
 */
public class producer {
    public static void main(String[] args) throws InterruptedException {

        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = null;
        try {
            channel = connection.createChannel();

            channel.queueDeclare("singleton_queue", true, false, false, null);

            channel.exchangeDeclare("singleton_exchange", "fanout");

            //生产者调用confirmselect将channel 设置为conf1xm模式
            //注意confirm模式跟事务机制不能在同一个队列中
            channel.confirmSelect();

            String msg = "hello,rabbitmq confirm model";

            channel.basicPublish("singleton_exchange", "singleton_queue", null, msg.getBytes());

            if (!channel.waitForConfirms()) {
                System.out.println("confirm message send failed");
            } else {
                System.out.println("confirm message send ok");
            }

        } catch (IOException e) {
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
