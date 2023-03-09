package com.atguigu.educenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantWxUtils implements InitializingBean {

    @Value("wxed9954c01bb89b47")
    //@Value("${wx.open.app_id}")
    private String appId;

    //@Value("${wx.open.app_secret}")
    @Value("a7482517235173ddb4083788de60b90e")
    private String appSecret;

    //@Value("${wx.open.redirect_url}")
    @Value("http://localhost:8160/api/ucenter/wx/callback")
    private String redirectUrl;

    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;


    @Override
    public void afterPropertiesSet() throws Exception {
        WX_OPEN_APP_ID = appId;
        WX_OPEN_APP_SECRET = appSecret;
        WX_OPEN_REDIRECT_URL = redirectUrl;
    }
}
