package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectDate;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-02-22
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {

        try {
            //得到文件的输入流
            InputStream inputStream = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(inputStream, SubjectDate.class, new SubjectExcelListener(eduSubjectService))
                    .sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        //根据前端需求数据的格式，设计对应的打包实现方法

        //1.查询所有一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", 0);
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
        //2.查询所有二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", 0);
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);
        //创建list，用于存储最终数据
        List<OneSubject> finalList = new ArrayList<>();
        //3.封装一级分类
        //把查询出来的一级分类list集合，进行遍历，取出来再放到最终集合finalList
        for (int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject subject = oneSubjectList.get(i);
            //把subject里面的值取出来，放到OneSubject对象里面
            //多个OneSubject放到finalList中去
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(subject, oneSubject);//简化的方法
            /*oneSubject.setId(subject.getId());
            oneSubject.setTitle(subject.getTitle());*/
            finalList.add(oneSubject);
            //4.封装二级分类，在一级分类的循环里面封装二级分类
            //创建list集合
            List<TwoSubject> twoSubjects = new ArrayList<>();
            for (int j = 0; j < twoSubjectList.size(); j++) {
                //获取每个二级分类
                EduSubject two = twoSubjectList.get(j);
                //判断二级分类属于哪一个一级分类
                //二级分类的parent_id和一级分类的id一样，则放到该一级分类下面去
                if (two.getParentId().equals(subject.getId())) {
                    TwoSubject t = new TwoSubject();
                    BeanUtils.copyProperties(two, t);
                    twoSubjects.add(t);
                }
            }
            oneSubject.setChildren(twoSubjects);
        }
        return finalList;
    }
}
