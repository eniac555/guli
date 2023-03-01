package vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

public class TestVodAuth {
    public static void main(String[] args) throws Exception{
        //根据视频id获得视频播放凭证

        //1.创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5t6UMxcC4gkSVDUqL3UL",
                "g8iKer6AwToQPwfB1AHO1Dakrw3hqM");

        //2.创建获取视频凭证的request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        //3.向request对象里面设置视频id
        request.setVideoId("f2693100b57371edbfe06733a78e0102");

        //4.调用初始化对象里面的方法，传递request，获取数据
        response = client.getAcsResponse(request);
        System.out.println("playauth: "+response.getPlayAuth());
    }

}
