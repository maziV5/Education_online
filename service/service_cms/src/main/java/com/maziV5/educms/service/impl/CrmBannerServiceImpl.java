package com.maziV5.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maziV5.educms.entity.CrmBanner;
import com.maziV5.educms.mapper.CrmBannerMapper;
import com.maziV5.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maziV5.utils.R;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author maziV5
 * @since 2023-04-02
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    //分页查询banner
    @Override
    public R getPageBanner(long current, long limit) {
        Page<CrmBanner> pageBanner = new Page<>(current,limit);

        this.page(pageBanner);

        return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    //查询所有banner
    @Cacheable(key = "'selectBannerList'",value = "banner")
    @Override
    public List<CrmBanner> getAllBanner() {
        //根据id进行降序排列，显示前4条记录
        QueryWrapper<CrmBanner> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        //last方法,拼接sql语句
        queryWrapper.last("limit 4");

        List<CrmBanner> list = this.list(queryWrapper);

        return list;
    }


}
