package com.lishuai.rabbitmq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 *
 * @author lishuai
 * @date 2022/12/3
 */
public class Consumer {
    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.30.137");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");

        Connection conenction = null;
        Channel channel = null;

        try {
            conenction = factory.newConnection("consumer");
            channel = conenction.createChannel();

            channel.queueDeclare("queue1", false, false, false, null);

//            DefaultConsumer consumer = new DefaultConsumer(channel){
//                /**
//                 * 回调方法。当收到消息之后会自动执行该方法
//                 * @param consumerTag 标识
//                 * @param envelope 获取一些信息，交换机，路由Key
//                 * @param properties 配置信息
//                 * @param body 数据
//                 * @throws IOException
//                 */
//                @Override
//                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                    System.out.println("consumerTag = " + consumerTag);
//                    System.out.println("Exchange = " + envelope.getExchange());
//                    System.out.println("RoutingKey = " + envelope.getRoutingKey());
//                    System.out.println("properties = " + properties);
//                    System.out.println("body = " + new String(body));
//                }
//            };

            /**
             * @param queue 通道名
             * @param autoAck 应答
             * @param callback 回调
             */
            channel.basicConsume("queue1", true, new DeliverCallback() {
                @Override
                public void handle(String s, Delivery delivery) throws IOException {
                    System.out.println("收到消息:" + new String(delivery.getBody(), "UTF-8"));
                }
            }, new CancelCallback() {
                @Override
                public void handle(String s) throws IOException {

                }
            });
            System.out.println("cg");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("出错了");
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

            if (conenction != null) {
                try {
                    conenction.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
