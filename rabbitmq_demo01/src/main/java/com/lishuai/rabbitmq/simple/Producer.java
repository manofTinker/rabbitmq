package com.lishuai.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lishuai
 * @date 2022/12/3
 */
public class Producer {
    public static void main(String[] args) {

        String host = "192.168.30.137";
        int port = 5672;
        String username = "admin";
        String password = "admin";

        //1.创建工厂连接
        ConnectionFactory factory = new ConnectionFactory();
        //2.设置连接属性
        factory.setHost(host);
        factory.setPort(port);
        factory.setVirtualHost("/");
        factory.setUsername(username);
        factory.setPassword(password);

        Connection connection = null;
        Channel channel = null;

        try {
            //3.从连接工厂中获取连接
            connection = factory.newConnection("生产者");
            //4.从连接中获取通道channel
            channel = connection.createChannel();

            //5.定义队列queue存储消息
            /**
             * 如果队列不存在则会创建队列
             * 【注意】不能创建两个相同名称的队列，不然会报错
             *
             * @params1: queue 队列名称
             * @params2: durable 队列是否持久化
             * @params3: exclusive 是否排他，即是否为私有，为true，会对当前队列加锁，其他的通道就甭能访问，并且自动关闭连接
             * @params4: autoDelete 是否自动删除，当最后一个消费者断开连接之后是否自动删除消息
             * @params5: arguments  可以设置队列附加参数，设置队列的有效期，消息的最大长度，队列的消息生命周期...
             */
//            public com.rabbitmq.client.impl.AMQImpl.Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) throws IOException {
//                validateQueueNameLength(queue);
//                return (com.rabbitmq.client.impl.AMQImpl.Queue.DeclareOk)this.exnWrappingRpc((new com.rabbitmq.client.AMQP.Queue.Declare.Builder()).queue(queue).durable(durable).exclusive(exclusive).autoDelete(autoDelete).arguments(arguments).build()).getMethod();
//            }
            channel.queueDeclare("queue1", false, false, false, null);

            //6.准备发送消息的内容
            String message = "hello,rabbitmq";

            //7.发送消息给rabbitmq-server
            /**
             * @params1: 交换机exchange
             * @params2: 队列名称/routing
             * @params3: 属性配置
             * @params4: 发送消息的内容
             */
            channel.basicPublish("", "queue1", null, message.getBytes());
            System.out.println("发送成功...");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("出现异常...");
        } finally {
            //8.释放连接并关闭通道
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
