package com.xuecheng.manage_cms_client.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 蔡闯王
 * @date 2019/1/31 20:04
 */
@Configuration
public class RabbitmqConfig {

    /**
     * 交换机的名称
     */
    public static final String EX_ROUTING_CMS_POSTPAGE = "ex_routing_cms_postpage";
    /**
     * 队列的名称
     */
    @Value("${xuecheng.mq.queue}")
    public String queue_cms_postpage_name;
    /**
     * routingKey 即站点Id
     */
    @Value("${xuecheng.mq.routingKey}")
    public String routingKey;

    /**
     * 声明交换机
     *
     * @return
     */
    @Bean
    public Exchange exchangeDeclare() {
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }

    /**
     * 声明队列
     *
     * @return
     */
    @Bean
    public Queue queueDeclare() {
        return new Queue(queue_cms_postpage_name);
    }

    /**
     * 绑定队列到交换机
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding queueBinding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }
}
