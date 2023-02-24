package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-02-23
 */
public interface EduChapterService extends IService<EduChapter> {

    //返回课程大纲列表的方法，根据课程id进行
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    //如何删除？
    //第一种：删除章节的时候，把章节里面的所有小节删除
    //第二种：不删除小节
    //这里使用第二种
    boolean deleteChapter(String chapterId);

}

