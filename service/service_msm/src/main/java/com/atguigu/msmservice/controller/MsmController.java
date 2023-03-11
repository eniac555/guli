package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
 
public class MsmController {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private MsmService msmService;

    //发送短信方法
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone){
        //1.从Redis获取验证码，如果Redis里面有，获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            return R.ok();
        }
        //2.如果获取不到，进行阿里云发送
        //生成随机值，由阿里云发送
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        //调用service发送短信的方法
        boolean isSend = msmService.send(param,phone);
        if (isSend){
            //如果发送成功，把发送成功的验证码，再放到Redis里面
            //redisTemplate.opsForValue().set(phone,code);

            //放到Redis里面，同时设置有效时间
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);//5分钟有效
            return R.ok();
        }else {
            return R.error().message("短信发送失败");
        }
    }
}
