package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-03-08
 */
public interface PayLogService extends IService<PayLog> {

    //订单支付二维码生成
    //比较固定的过程，基本复制代码
    //参数是订单号
    Map createNative(String orderNo);

    //根据订单号查询订单支付状态
    Map<String, String> queryPayStatus(String orderNo);

    //向支付表中添加记录，并更新订单状态
    void updateOrdersStatus(Map<String, String> map);
}
