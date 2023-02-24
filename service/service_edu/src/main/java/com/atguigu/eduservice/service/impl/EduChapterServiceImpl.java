package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-02-23
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;//


    //返回课程大纲列表的方法，根据课程id进行
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1.根据课程id，查询出课程里面所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //2.根据课程id，查询小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        //创建集合，存放最终的封装数据
        List<ChapterVo> finalList = new ArrayList<>();

        //3.遍历章节list集合，进行封装
        for (int i = 0; i < eduChapterList.size(); i++) {
            //得到每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            //值的复制
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finalList.add(chapterVo);

            //4.遍历小节list集合，进行封装
            //创建集合，用于封装章节中的小节
            List<VideoVo> videoVoList = new ArrayList<>();
            for (int j = 0; j < eduVideoList.size(); j++) {
                //得到每个小节
                EduVideo eduVideo = eduVideoList.get(j);
                //判断小节里面的chapterid和章节里面的id是否一样
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVoList.add(videoVo);
                }

            }
            chapterVo.setChildren(videoVoList);
        }

        return finalList;
    }

    //章节如何删除？
    //第一种：删除章节的时候，把章节里面的所有小节删除
    //第二种：不删除小节
    //这里使用第二种
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterI的，查询小节表，如果查询到有数据，不进行删除
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id",chapterId);
        int count = videoService.count(queryWrapper);//有多少小节
        if (count>0){//不删
            throw  new GuliException(20001,"不能删除");
        }else {//删除
            int result = baseMapper.deleteById(chapterId);
                return result>0;
        }
    }
}
