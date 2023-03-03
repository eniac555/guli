package com.atguigu.msmservice.service.impl;

import com.atguigu.msmservice.service.MsmService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {


    //发送短信方法
    @Override
    public boolean send(Map<String, Object> param,String phone) {
        return true;
    }
}
