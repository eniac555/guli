package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Component//交给spring管理
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
//被调用的服务的名字，具体名字在application.properties文件里
//fallback表示出错会调用这个类里面的方法
public interface VodClient {

    //定义要调用方法的路径

    //根据视频id删除阿里云中的视频
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")//路径写全
    public R removeAlyVideo(@PathVariable("id") String id);
    //@PathVariable注解一定要指定参数名称，否则出错


    //定义删除多个视频的方法
    @DeleteMapping("/eduvod/video/delete-batch")//路径写全
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
    //@PathVariable注解一定要指定参数名称，否则出错

}
