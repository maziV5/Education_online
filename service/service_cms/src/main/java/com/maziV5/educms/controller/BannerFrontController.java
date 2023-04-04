package com.maziV5.educms.controller;


import com.maziV5.educms.entity.CrmBanner;
import com.maziV5.educms.service.CrmBannerService;
import com.maziV5.utils.R;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前台控制器
 * </p>
 *
 * @author maziV5
 * @since 2023-04-02
 */
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {
    @Autowired
    public CrmBannerService bannerService;

    //查询banner
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list = bannerService.getAllBanner();

        return R.ok().data("list",list);
    }
}

