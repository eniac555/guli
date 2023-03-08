package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-03-08
 */
@RestController
@CrossOrigin
@RequestMapping("/eduorder/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //1.生成订单的方法
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        //HttpServletRequest request 通过这个取到已经登录并在准备支付订单的用户的信息
        //因为要支付，肯定是提前已经登录的

        //创建订单，返回订单号

        String orderNo = orderService.createOrders(courseId,
                                                   JwtUtils.getMemberIdByJwtToken(request));

        return R.ok().data("orderId",orderNo);
    }


    //2.根据订单id获得订单信息，用于订单详情页面的显示
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }



}

