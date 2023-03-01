package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-02-23
 */
@Api(description = "添加小节")
@RestController
@CrossOrigin
@RequestMapping("/eduservice/video")//对应前端.js文件中的路径
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    //注入vodClient
    @Autowired
    private VodClient vodClient;

    //1.添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }


    //2.删除小节
    //TODO 后面这个需要完善，删除小节的时候，同时把里面的视频删除  已完成
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //根据小节id获得视频id，再进行删除
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //要判断是否有视频
        if (!StringUtils.isEmpty(videoSourceId)) {
            //根据视频id，远程调用实现删除
            R result = vodClient.removeAlyVideo(videoSourceId);
            if (result.getCode()==20001){
                throw new GuliException(20001,"删除视频失败-熔断器");
            }
        }
        //删除小节  先删视频，不然小节没了，就查不到视频
        videoService.removeById(id);
        return R.ok();
    }



    //3.修改小节 todo
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }


}

