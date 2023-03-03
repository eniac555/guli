package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    //发送短信方法
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone){
        //生成随机值，由阿里云发送
        String code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        //调用service发送短信的方法
        boolean isSend = msmService.send(param,phone);
        if (isSend){
            return R.ok();
        }else {
            return R.error().message("短信发送失败");
        }
    }
}
