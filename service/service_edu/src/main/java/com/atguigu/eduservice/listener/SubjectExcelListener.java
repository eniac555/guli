package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectDate;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<SubjectDate> {

    //因为SubjectExcelListener不能交给spring进行管理，需要自己new，不能注入其他对象
    //不能实现数据库操作

    /*
    不是很懂这部分怎么来的，，，，，，
     */

    public EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }


    //读取Excel中的内容，逐行
    @Override
    public void invoke(SubjectDate subjectDate, AnalysisContext analysisContext) {
        if (subjectDate == null) {
            throw new GuliException(20001, "文件为空");
        }

        //因为是逐行读取，每次读取都有两个值，第一个值是一级分类，第二个值是二级分类
        //判断一级分类
        EduSubject oneSubject = this.existOneSubject(subjectService, subjectDate.getOneSubjectName());
        if (oneSubject == null) {
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectDate.getOneSubjectName());//设置一级分类名称
            subjectService.save(oneSubject);
        }
        //添加二级分类
        String pid = oneSubject.getId();//获取一级分类的id值
        EduSubject twoSubject = this.existTwoSubject(subjectService, subjectDate.getTwoSubjectName(), pid);
        if (twoSubject == null) {
            twoSubject = new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectDate.getTwoSubjectName());//设置一级分类名称
            subjectService.save(twoSubject);

        }

    }



    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject (EduSubjectService subjectService, String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", 0);
        EduSubject one = subjectService.getOne(wrapper);
        return one;
    }


    //判断二级分类不能重复添加
    private EduSubject existTwoSubject (EduSubjectService subjectService, String name, String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject two = subjectService.getOne(wrapper);
        return two;
    }
}
