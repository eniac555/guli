package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-03-08
 */

@RestController
 
@RequestMapping("/eduorder/paylog")
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    //订单支付二维码生成
    //比较固定的过程，基本复制代码
    //参数是订单号
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        //返回信息，包含二维码地址，还有其他的信息
        Map map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }


    //查询支付状态的接口，支付成功要在支付日志加记录，并修改订单表中的支付状态
    //参数是订单号
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        if (map==null){
            return R.error().message("支付失败");

        }
        //如果返回的map不为空，通过map获取订单状态
        if (map.get("trade_state").equals("SUCCESS")){
            //支付成功，添加记录并修改支付状态
            payLogService.updateOrdersStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }



}

