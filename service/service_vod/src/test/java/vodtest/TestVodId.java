package vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;

import java.util.List;

public class TestVodId {
    public static void main(String[] args) throws Exception{
        //根据视频id获取视频播放地址

        //1.创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5t6UMxcC4gkSVDUqL3UL",
                "g8iKer6AwToQPwfB1AHO1Dakrw3hqM");

        //2.创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //3.向request对象里面设置视频id
        request.setVideoId("f2693100b57371edbfe06733a78e0102");

        //4.调用初始化对象里面的方法，传递request，获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
