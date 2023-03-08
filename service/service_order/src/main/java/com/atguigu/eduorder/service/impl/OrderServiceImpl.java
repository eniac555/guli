package com.atguigu.eduorder.service.impl;

import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.eduorder.client.EduClient;
import com.atguigu.eduorder.client.UcenterClient;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.mapper.OrderMapper;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.eduorder.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-03-08
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    //1.生成订单的方法
    @Override
    public String createOrders(String courseId, String memberId) {
        //通过远程调用，利用用户id获取到用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);

        //通过远程调用，利用课程id获取到课程信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        //把值取出来，加到数据库里面去
        //创建order对象，向对象里面设置需要的数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId);//课程id
        order.setCourseTitle(courseInfoOrder.getTitle());//课程标题
        order.setCourseCover(courseInfoOrder.getCover());//课程封面
        order.setTeacherName("test");//讲师名称
        order.setTotalFee(courseInfoOrder.getPrice());//课程价格
        order.setMemberId(memberId);//用户id
        order.setMobile(userInfoOrder.getMobile());//用户手机号
        order.setNickname(userInfoOrder.getNickname());//用户昵称
        order.setStatus(0);//支付状态
        order.setPayType(1);//支付类型，默认微信
        baseMapper.insert(order);


        //返回订单号
        return order.getOrderNo();
    }
}
