package com.atguigu.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/*
启动的时候会去找数据库的配置，但是这个模块因为不用操作数据库，只做上传到oss的功能
没有配置数据库，解决方案有两个
1.添加数据库相关配置
2.在启动类添加属性，默认不去配置数据库（推荐） exclude = DataSourceAutoConfiguration.class
 */

@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.atguigu"})
public class OssApplication {
    public static void main(String[] args) {

        SpringApplication.run(OssApplication.class, args);
    }
}
