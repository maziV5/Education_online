package com.maziV5.educenter.controller;

import com.google.gson.Gson;
import com.maziV5.educenter.entity.UcenterMember;
import com.maziV5.educenter.service.UcenterMemberService;
import com.maziV5.educenter.utils.ConstantWxUtils;
import com.maziV5.educenter.utils.HttpClientUtils;
import com.maziV5.utils.JwtUtils;
import com.maziV5.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@RestController
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {
    @Autowired
    private UcenterMemberService memberService;

    @GetMapping("callback")
    public ModelAndView callback(String code,String state){
        try {
            //1.获取code

            //用code获取 access_token和openid
            String baseUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            String accessTokenUrl = String.format(
                    baseUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );

            //请求url地址,httpclient发送请求

            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //获取 access_token和openid
            Gson gson = new Gson();
            HashMap map = gson.fromJson(accessTokenInfo, HashMap.class);
            String openid = (String) map.get("openid");
            String accessToken = (String) map.get("access_token");



            //判断数据库是否存在相同openid
            UcenterMember member = memberService.getOpenId(openid);

            if (member == null) {

                //获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";

                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        accessToken,
                        openid
                );

                String userInfo = HttpClientUtils.get(userInfoUrl);

                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String) userInfoMap.get("headimgurl");

                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);

                memberService.save(member);
            }

            String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            ModelAndView modelAndView = new ModelAndView("redirect:http://localhost:3000/?token=" + token);
            return modelAndView;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @GetMapping("login")
    public R getWxCode(){
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //对redirect_uri编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "maziV5"
        );

        //重定向到请求微信地址
        return R.ok().data("url",url);
    }

}
