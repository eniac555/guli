package com.atguigu.msmservice.service;

import java.util.Map;

public interface MsmService {

    //发送短信方法
    boolean send(Map<String, Object> param, String phone);
}
