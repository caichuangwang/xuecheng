package com.xuecheng.consumertest;

import com.rabbitmq.client.*;
import com.rabbitmq.client.Consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 蔡闯王
 * @date 2019/1/31 17:09
 */
public class Consumer_routing_sms {
    //声明队列
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_ROUTING_INFORM = "exchange_routing_inform";
    private static final String ROUTINGKEY_EMAIL = "inform_email";
    private static final String ROUTINGKEY_SMS = "inform_sms";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        //1.创建连接对象工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟主机
        connectionFactory.setVirtualHost("/");
        //2.获取连接
        try {
            connection = connectionFactory.newConnection();
            //获取通道对象
            channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_ROUTING_INFORM, ROUTINGKEY_SMS);

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "utf-8");
                    System.out.println(message);
                }
            };
            channel.basicConsume(QUEUE_INFORM_SMS, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
