package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;

import com.atguigu.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-02-19
 */


@Api(description = "讲师管理")//
@CrossOrigin//解决跨域
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    //访问地址：http://localhost:8001/eduservice/edu-teacher/findAll
    //测试地址：http://localhost:8001/swagger-ui.html

    //把service注入
    @Autowired
    private EduTeacherService teacherService;

    //1.查询讲师所有数据
    //rest风格
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R finaAllTeacher() {
        //调用service方法实现查询所有
        List<EduTeacher> eduTeachers = teacherService.list(null);
        return R.ok().data("items", eduTeachers);
    }


    //2.逻辑删除讲师方法
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")  //表示id需要通过该路径进行传递
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
                           @PathVariable String id) {//获取到路径中输入的id值
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


    //3.讲师分页查询方法
    //current：当前页
    //limit：记录数
    @ApiOperation(value = "讲师列表分页")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);//当前是第current页，有limit个记录数

        try {
            int i = 10 / 0;
        } catch (Exception e) {
            //执行自定义异常
            throw new GuliException(20001, "执行了自定义异常处理");
        }

        /*调用方法实现分页
        调用方法时，底层会进行封装，把分页所有数据封装到pageTeacher对象里去*/
        teacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合

        return R.ok().data("total", total).data("rows", records);

        /*
        Map map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);
        */
    }

    //4.多条件组合查询+分页
    @ApiOperation(value = "多条件讲师查询")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        //RequestBody：使用json传递数据，再把json数据封装到对应的对象里面去，对应前面需要改成PostMapping
        //required = false，表示参数可以为空

        //创建page对象
        Page<EduTeacher> teacherPage = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询
        //mybatis学过动态sql
        //判断条件值是否为空
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        //调用方法实现条件查询+分页
        teacherService.page(teacherPage, wrapper);
        long total = teacherPage.getTotal();//总记录数
        List<EduTeacher> records = teacherPage.getRecords();//数据list集合
        return R.ok().data("total", total).data("rows", records);
    }


    //5.添加讲师
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }


    //6.根据id查询讲师
    @ApiOperation(value = "根据id查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }


    //7.更新讲师信息
    @ApiOperation(value = "更新讲师信息")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.updateById(eduTeacher);
        //传进去一个JSON数据表示的讲师对象，然后修改里面的数据，其中时间删掉，因为会自动生成，
        //要有id，因为需要根据id来找到对应数据，再修改其他信息
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }


}

