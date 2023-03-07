package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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



    //1.条件查询带分页的课程列表功能
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不空则拼接条件
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            //判断一级分类是否存在
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            //判断二级分类是否存在
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            //判断关注度
            wrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
            //判断时间排序
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
            //判断价格是否进行排序
            wrapper.orderByDesc("price");
        }


        baseMapper.selectPage(pageParam, wrapper);
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//是否有下一页
        boolean hasPrevious = pageParam.hasPrevious();//是否有上一页

        //把分页数据取出，放进map集合，返回
        Map<String,Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;

    }


    //1.根据课程id，编写SQL语句，进行查询
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
