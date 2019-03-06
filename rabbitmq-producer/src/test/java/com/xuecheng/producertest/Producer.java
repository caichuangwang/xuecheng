package com.xuecheng.producertest;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 蔡闯王
 * @date 2019/1/31 15:18
 */
public class Producer {
    //队列名称
    private static final String QUEUE = "test";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            //1.创建连接对象工厂
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("127.0.0.1");
            connectionFactory.setPort(5672);
            connectionFactory.setUsername("guest");
            connectionFactory.setPassword("guest");
            //设置虚拟主机
            connectionFactory.setVirtualHost("/");
            //2.获取连接
            connection = connectionFactory.newConnection();
            //获取通道对象
            channel = connection.createChannel();
            //3.声明队列
            channel.queueDeclare(QUEUE, true, false, false, null);
            //4.发送消息
            String message = "send test message";
            channel.basicPublish("", QUEUE, null, message.getBytes());

            System.out.println("send message success");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
