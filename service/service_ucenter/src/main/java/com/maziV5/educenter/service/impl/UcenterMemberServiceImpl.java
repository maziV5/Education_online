package com.maziV5.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maziV5.educenter.entity.UcenterMember;
import com.maziV5.educenter.entity.vo.RegisterVo;
import com.maziV5.educenter.mapper.UcenterMemberMapper;
import com.maziV5.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maziV5.servicebase.exceptionhandler.GuliException;
import com.maziV5.utils.JwtUtils;
import com.maziV5.utils.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.xml.ws.Action;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author maziV5
 * @since 2023-04-02
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //用户登录
    @Override
    public String login(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"手机号或密码为空");
        }

        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        UcenterMember mobileMember = this.getOne(queryWrapper);

        //判断手机号
        if (mobileMember == null) {
            throw new GuliException(20001,"登陆失败");
        }

        //判断密码
        //MD5加密
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new GuliException(20001,"登陆失败");
        }

        //判断是否被禁用
        if (mobileMember.getIsDisabled()){
            throw new GuliException(20001,"登陆失败");

        }

        //生成token
        String token = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return token;

    }

    //用户注册
    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String nickname = registerVo.getNickname();

        //非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)){
            throw new GuliException(20001,"注册失败");
        }

        //验证码判断
        String redisCode = stringRedisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)){
            throw new GuliException(20001,"验证码错误");
        }

        //手机号重复判断
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        int count = this.count(queryWrapper);

        if (count > 0) {
            throw new GuliException(20001,"该手机号已注册");
        }

        //添加用户
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setNickname(nickname);
        ucenterMember.setAvatar("https://ma-edu.oss-cn-chengdu.aliyuncs.com/user/5e83f590ee56663c6fa218246b259d69e896a1ae.jpg");

        this.save(ucenterMember);
    }

    //根据openid查询
    @Override
    public UcenterMember getOpenId(String openid) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);

        UcenterMember member = this.getOne(queryWrapper);

        return member;
    }
}
