package com.maziV5.educenter.controller;


import com.maziV5.educenter.entity.UcenterMember;
import com.maziV5.educenter.entity.vo.RegisterVo;
import com.maziV5.educenter.service.UcenterMemberService;
import com.maziV5.utils.JwtUtils;
import com.maziV5.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author maziV5
 * @since 2023-04-02
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    // 用户注册
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);

        return R.ok();
    }

    // 用户登录
    @PostMapping("login")
    public R userLogin(@RequestBody UcenterMember member){
        String token = memberService.login(member);
        System.out.println(token);

        return R.ok().data("token",token);
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    private R getMemberInfo(HttpServletRequest request){
        //jwt工具类获取用户id
        String id = JwtUtils.getMemberIdByJwtToken(request);

        UcenterMember member = memberService.getById(id);

        return R.ok().data("userInfo",member);
    }

}

