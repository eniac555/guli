package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-03-04
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    //登录的方法
    String login(UcenterMember member);

    //注册
    void register(RegisterVo registerVo);

    //根据openID判断用户是否存在，并取出用户
    UcenterMember getOpenIdMember(String openid);

    //创建统计当日注册人数的方法，用于生成统计表中的数据，然后统计模块远程调用这个方法获得数据
    Integer countRegisterDay(String day);
}
