package com.maziV5.educms.service;

import com.maziV5.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maziV5.utils.R;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author maziV5
 * @since 2023-04-02
 */
public interface CrmBannerService extends IService<CrmBanner> {

    R getPageBanner(long current, long limit);


    List<CrmBanner> getAllBanner();
}
