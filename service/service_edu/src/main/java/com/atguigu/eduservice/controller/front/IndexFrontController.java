package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {
    //查询热门课程和名师的接口

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;


    //查询前八条热门课程   查询前四个名师
    @GetMapping("index")
    public R index(){
        //查询前八条热门课程
        QueryWrapper<EduCourse> wrapper1 = new QueryWrapper<>();
        wrapper1.orderByDesc("id");
        wrapper1.last("limit 8");
        List<EduCourse> eduList = courseService.list(wrapper1);

        //查询前四个名师
        QueryWrapper<EduTeacher> wrapper2 = new QueryWrapper<>();
        wrapper2.orderByAsc("id");
        wrapper2.last("limit 4");
        List<EduTeacher> eduTeacherList = teacherService.list(wrapper2);

        //返回结果
        return R.ok().data("eduList",eduList).data("teacherList",eduTeacherList);
    }

}
