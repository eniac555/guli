package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-02-23
 */

@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //注入课程描述的服务
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    //注入章节和小节的服务
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    //添加课程基本信息的方法
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //需要向两个表加信息
        //1.向课程表添加课程基本信息
        //把CourseInfoVo转换为EduCourse
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);//影响行数
        if (insert==0){
            throw new GuliException(20001,"添加课程失败");
        }

        //得到添加课程的id
        String id = eduCourse.getId();

        //2.向课程表添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        eduCourseDescription.setId(id);//课程和描述是一对一的
        boolean save = courseDescriptionService.save(eduCourseDescription);

        return id;
    }

    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        //1.查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2.查询课程描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1.修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update==0){
            throw new GuliException(20001,"修改课程信息失败");
        }

        //2.修改描述表格
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        courseDescriptionService.updateById(eduCourseDescription);
    }


    //根据课程id返回课程最终确认信息，查询多张表
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用mapper
        CoursePublishVo coursePublishInfo = baseMapper.getCoursePublishInfo(id);
        return coursePublishInfo;
        //调用getCoursePublishInfo方法时，先回到mapper里面，
        //然后再去mapper对应的xml文件里面查找对应的SQL语句
        //执行相应的操作
    }


    //删除课程
    @Override
    public void removeCourse(String courseId) {
        /*
        1.根据id先删除小节
        2.根据id删除章节
        3.根据id删除描述
        4.删除课程本身
         */

        //1.根据id先删除小节
        eduVideoService.removeVideoCourseId(courseId);//自定义方法

        //2.根据id删除章节
        eduChapterService.removeChapterCourseId(courseId);//自定义方法

        //3.根据id删除描述
        courseDescriptionService.removeById(courseId);

        //4.删除课程本身
        int result = baseMapper.deleteById(courseId);

        if (result==0){
            throw new GuliException(20001,"删除失败");
        }
    }
}
