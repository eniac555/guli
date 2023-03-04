package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
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
@CrossOrigin
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


}

