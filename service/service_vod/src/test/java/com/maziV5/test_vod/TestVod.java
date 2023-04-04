package com.maziV5.test_vod;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;

import java.util.List;

public class TestVod {
    //根据视频id获取视频播放地址
    public static void main(String[] args) throws ClientException {

        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tECjrikxQfFD6ViPap3", "f4uoywXLco4KbDi262wBMU2ZnwBdeG");

        //创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //向request对象里设置视频id
        request.setVideoId("9f0a8190cfcb71ed80446732b68e0102");

        //调用初始化对象里的方法，传递request，获取数据
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
