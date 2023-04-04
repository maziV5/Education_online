package com.maziV5.test_vod;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

public class TestVod2 {
    //根据视频id获取视频播放播放凭证
    public static void main(String[] args) throws ClientException {
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tECjrikxQfFD6ViPap3", "f4uoywXLco4KbDi262wBMU2ZnwBdeG");

        //创建获取视频地址request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        //向request里设置视频的id值
        request.setVideoId("9f0a8190cfcb71ed80446732b68e0102");

        //调用client对象的方法得到凭证
        response = client.getAcsResponse(request);

        System.out.println("PlayAuth: " + response.getPlayAuth());

    }
}
