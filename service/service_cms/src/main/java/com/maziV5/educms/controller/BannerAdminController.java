package com.maziV5.educms.controller;


import com.maziV5.educms.entity.CrmBanner;
import com.maziV5.educms.service.CrmBannerService;
import com.maziV5.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 后台控制器
 * </p>
 *
 * @author maziV5
 * @since 2023-04-02
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
//@EnableDiscoveryClient
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;

    //分页查询banner
    @GetMapping("pageBanner/{current}/{limit}")
    public R pageBanner(@PathVariable long current,@PathVariable long limit){
        return bannerService.getPageBanner(current,limit);
    }

    //增加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.save(crmBanner);

        return R.ok();
    }

    //删除banner
    @DeleteMapping("deleteBanner/{id}")
    public R deleteBanner(@PathVariable String id){
        bannerService.removeById(id);

        return R.ok();
    }

    //修改banner
    @PostMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        bannerService.updateById(crmBanner);

        return R.ok();
    }

    //根据id查询banner
    @GetMapping("getBanner/{id}")
    public R getBanner(@PathVariable String id){
        CrmBanner crmBanner = bannerService.getById(id);

        return R.ok().data("banner",crmBanner);
    }
}

