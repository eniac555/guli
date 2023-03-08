package com.atguigu.eduorder.client;

import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-edu")//名字是配置文件里的模块名
public interface EduClient {

    //根据课程id获得课程信息，这个接口用来被创建订单时远程调用使用
    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{courseId}")//完整地址
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("courseId") String courseId);
}
