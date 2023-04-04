package com.maziV5.educenter.service;

import com.maziV5.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maziV5.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author maziV5
 * @since 2023-04-02
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getOpenId(String openid);
}
