package com.maziV5.msmservice.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.maziV5.msmservice.service.MsmService;
import com.maziV5.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
public class MsmServiceImpl implements MsmService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //使用aliyun发送验证码
    @Override
    public boolean send(String code, String phone) {

        if (StringUtils.isEmpty(code)){
            return false;
        }

        String redisCode = stringRedisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(redisCode)) {
            return true;
        }

        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI5tECjrikxQfFD6ViPap3", "f4uoywXLco4KbDi262wBMU2ZnwBdeG");
        DefaultAcsClient client = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setSignName("验证码使用学习");
        request.setTemplateCode("SMS_275200493");
        request.setTemplateParam("{\"code\":\""+code+"\"}");

        try {
            SendSmsResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));

            if (response.getMessage().equals("OK")) {

                stringRedisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
                return true;
            }
        } catch (ServerException e) {
            e.printStackTrace();
            return false;
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
            return false;
        }
        return false;

    }
}
