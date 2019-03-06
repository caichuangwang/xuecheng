package com.xuecheng.consumertest;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 蔡闯王
 * @date 2019/1/31 15:39
 */
public class Consumer {
    //声明队列
    private static final String QUEUE = "test";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            //创建连接工程对象
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("127.0.0.1");
            connectionFactory.setPort(5672);
            //2.获取连接对象
            connection = connectionFactory.newConnection();
            //获取通道对象
            channel = connection.createChannel();
            //3.声明队列
            channel.queueDeclare(QUEUE, true, false, false, null);
            //4.监听消息队列
            channel.basicConsume(QUEUE, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "utf-8");
                    System.out.println(message);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
