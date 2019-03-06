package com.xuecheng.manage_cms.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 蔡闯王
 * @date 2019/1/31 21:15
 */
@Configuration
public class RabbitmqConfig {
    /**
     * 交换机的名称
     */
    public static final String EX_ROUTING_CMS_POSTPAGE="ex_routing_cms_postpage";

    @Bean
    public Exchange exchangeDeclare(){
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }
}
