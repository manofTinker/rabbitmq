package com.lishuai.ttl.queueTTL;

import com.lishuai.rabbitmq.util.RabbitmqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * 从队列中设置过期时间
 *
 * @author lishuai
 * @date 2022/12/6
 */
public class queueTTLProducer {
    public static void main(String[] args) throws IOException {

        Connection connection = RabbitmqUtil.getConnection();

        Channel channel = connection.createChannel();

        HashMap<String, Object> map = new HashMap<>();
        map.put("x-message-ttl",9000);
        map.put("x-dead-letter-exchange", "queue-deadletter-ttl");
        map.put("x-dead-letter-routing-key", "queue-deadroutingkey-ttl");

        channel.queueDeclare("queue-deadletter-ttl1",false,false,false,null);

        channel.exchangeDeclare("queue-deadletter-ttl","fanout");

        channel.queueBind("queue-deadletter-ttl1","queue-deadletter-ttl","queue-deadroutingkey-ttl");

        channel.queueDeclare("queue-ttl", false, false, false, map);

        for (int i = 0; i < 20; i++) {
            String message = "队列ttl" + i;
            channel.basicPublish("", "queue-ttl", null, message.getBytes());
            System.out.println("成功！");
        }

        if (channel != null && channel.isOpen()) {
            try {
                channel.close();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            connection.close();
        }

    }
}
