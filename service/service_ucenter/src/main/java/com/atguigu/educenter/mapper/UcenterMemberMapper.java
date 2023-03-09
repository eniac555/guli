package com.atguigu.educenter.mapper;

import com.atguigu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2023-03-04
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {


    //创建统计当日注册人数的方法，用于生成统计表中的数据，然后统计模块远程调用这个方法获得数据
    Integer countRegisterDay(String day);
}
