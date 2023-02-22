package com.atguigu.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//当项目一启动，spring中有个接口，spring加载之后，执行接口中的一个方法

@Component//表示交给spring进行管理
public class ConstantPropertiesUtils implements InitializingBean {


    //读取配置文件的内容
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    //通过注解@value，会把大括号中的变量读取出来，赋值给endpoint

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;


    //定义公开的静态常量
    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
