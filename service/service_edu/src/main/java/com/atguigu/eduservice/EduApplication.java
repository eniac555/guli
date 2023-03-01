package com.atguigu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


//启动类，建在四个包(控制器，实体，服务，mapper)的外面
@EnableFeignClients//调用端的启动类添加注解
@EnableDiscoveryClient//nacos注册
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})//加上这个后能扫描到common里面的配置类
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
