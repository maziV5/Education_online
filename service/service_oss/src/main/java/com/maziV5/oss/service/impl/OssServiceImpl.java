package com.maziV5.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.maziV5.oss.service.OssService;
import com.maziV5.oss.utils.ConstantPPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        String endpoint = ConstantPPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPPropertiesUtils.BUCKET_NAME;

        String fileName = file.getOriginalFilename();

        //生成文件名称唯一值
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        fileName = uuid + fileName;

        //把文件按日期分类
        String datePath = new DateTime().toString("yyyy/MM/dd");
        fileName = datePath + "/" + fileName;

        System.out.println(fileName);

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = file.getInputStream();
            // 创建PutObjectRequest对象。
            //第一个参数  bucket名称
            //第二个参数  上传到oss文件路径和文件名称
            //第三个参数  上传文件输入流
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream);
            // 设置该属性可以返回response。如果不设置，则返回的response为空。
            putObjectRequest.setProcess("true");
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            // 如果上传成功，则返回200。
            System.out.println(result.getResponse().getStatusCode());

            System.out.println("-----------------");
            System.out.println(result.getResponse().getUri());
            System.out.println("-----------------");

            //返回上传文件路径
            //https://ma-edu.oss-cn-chengdu.aliyuncs.com/kele.jpg
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;

            return url;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        return null;
    }
}
