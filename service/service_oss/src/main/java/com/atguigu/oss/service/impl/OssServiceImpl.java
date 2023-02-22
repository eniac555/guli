package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    //上传头像到OSS
    public String uploadFileAvatar(MultipartFile file) {
        //工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            //获取上传文件的输入流
            InputStream inputStream = file.getInputStream();

            String originalFilename = file.getOriginalFilename(); //获取文件名称

            /*
            文件名称可能会相同，会把之前的文件进行覆盖，这是不允许的
             */
            //1.在文件名里添加随机唯一值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");//去掉连接符
            //2.uuid加到原来的文件名中来
            originalFilename = uuid + originalFilename;

            //文件按照日期分类
            // 2023/02/21  三级文件夹
            //获取当前日期
            /*Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");*/

            String date = new DateTime().toString("yyyy/MM/dd");
            originalFilename = date + "/" + originalFilename;

            //调用OSS中的方法实现上传
            //第一个参数：bucketName
            //第二个参数：上传到OSS的文件路径和文件名称
            ossClient.putObject(bucketName, originalFilename, inputStream);
            //关闭OSS对象
            ossClient.shutdown();

            //https://edu-gqc.oss-cn-beijing.aliyuncs.com/aaa.jpg 路径示例
            //把上传之后的文件路径进行返回
            //需要把上传到OSS的路径手动拼接
            String url = "https://" + bucketName + "." + endpoint + "/" + originalFilename;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
