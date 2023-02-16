package com.lishuai.comfirm.singleton;

import com.lishuai.rabbitmq.util.RabbitmqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author lishuai
 * @date 2022/12/7
 */
public class consumer {
    public static void main(String[] args) throws IOException {

        Connection connetion = RabbitmqUtil.getConnection();
        Channel channel = connetion.createChannel();
        //队列声明
        channel.queueDeclare("singleton_queue", false, false, false, null);
        //声明消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            //消息到达，触发此方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("[confirm] Recv msg = " + msg);
            }
        };

        //监听队列
        channel.basicConsume("singleton_queue", true, consumer);
    }
}
