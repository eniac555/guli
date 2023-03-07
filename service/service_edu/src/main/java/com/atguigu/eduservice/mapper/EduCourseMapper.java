package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2023-02-23
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    //根据课程id返回课程最终确认信息，查询多张表
    CoursePublishVo getCoursePublishInfo(String courseId);


    //1.根据课程id，编写SQL语句，进行查询
    CourseWebVo getBaseCourseInfo(String courseId);
}
