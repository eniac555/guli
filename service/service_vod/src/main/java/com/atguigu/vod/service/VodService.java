package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {


    //上传视频到阿里云
    String uploadVideoAly(MultipartFile file);



    //删除多个阿里云中的视频
    //删除课程同时删除视频，可能对应多个视频
    //参数是多个视频id
    void removeMoreAlyVideo(List<String> videoIdList);

}
