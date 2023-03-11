package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-03-04
 */
@RestController
@RequestMapping("/educenter/member")
 
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;


    //1.登录的方法
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member){
        //调用service中的方法实现登录
        //返回token值，使用JWT生成
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    //2.注册的方法
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }


    //3.根据token获取用户信息，头像和昵称，用于页面显示
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        //调用jwt工具类方法，根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库，根据用户id获取头像和昵称
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }


    //根据用户id获得用户信息，这个接口用来被创建订单时远程调用使用
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){
        //UcenterMemberOrder，这个类的作用就是让在被调用端和调用端返回相同的对象，方便取数据
        //所以在common里面新建了这个类，让不同模块都能使用
        UcenterMember member = memberService.getById(id);
        UcenterMemberOrder memberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,memberOrder);

        return memberOrder;
    }


    //创建统计当日注册人数的方法，用于生成统计表中的数据，然后统计模块远程调用这个方法获得数据
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day){
        Integer count = memberService.countRegisterDay(day);
        return R.ok().data("countRegister",count);
    }




}

