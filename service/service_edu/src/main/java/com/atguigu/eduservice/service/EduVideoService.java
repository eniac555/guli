package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-02-23
 */
public interface EduVideoService extends IService<EduVideo> {

    //根据id先删除小节
    void removeVideoCourseId(String courseId);
}
