package com.maziV5.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.maziV5.service.VodService;
import com.maziV5.servicebase.exceptionhandler.GuliException;
import com.maziV5.utils.ConstantVodUtils;
import com.maziV5.utils.InitVodClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    //上传视频
    @Override
    public String uploadVideo(MultipartFile file) {
        /**
         *  阿里云视频点播 流式上传接口
         *
         * @param accessKeyId
         * @param accessKeySecret
         * @param title 上传后文件名称
         * @param fileName 上传文件原始名称
         * @param inputStream 上传文件输入流
         */


        try {
            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0,fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.KEY_ID, ConstantVodUtils.KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;

            if (response.isSuccess()) {
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }

            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //删除视频
    @Override
    public void deleteVideo(String id) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.KEY_ID, ConstantVodUtils.KEY_SECRET);

            //创建request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request里设置视频id
            System.out.println(id);
            request.setVideoIds(id);

            //调用client方法
            client.getAcsResponse(request);

        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    //删除多个视频
    //参数多个视频ID
    @Override
    public void deleteMoreVideo(List idList) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.KEY_ID, ConstantVodUtils.KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();

            //idList转换
            String ids = StringUtils.join(idList.toArray(), ",");

            request.setVideoIds(ids);

            client.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add("33");

        String s = StringUtils.join(list.toArray(), ",");

        System.out.println(s);
    }
}
