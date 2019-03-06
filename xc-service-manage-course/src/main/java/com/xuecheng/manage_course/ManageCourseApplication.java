package com.xuecheng.manage_course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 蔡闯王
 * @date 2019/1/31 21:59
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EntityScan("com.xuecheng.framework.domain.course")
@ComponentScan(basePackages = {"com.xuecheng.api", "com.xuecheng.framework", "com.xuecheng.manage_course"})
public class ManageCourseApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCourseApplication.class, args);
    }
}
