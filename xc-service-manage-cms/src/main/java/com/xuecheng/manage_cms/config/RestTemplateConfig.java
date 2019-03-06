package com.xuecheng.manage_cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author 蔡闯王
 * @date 2019/2/6 10:45
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate RestTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
