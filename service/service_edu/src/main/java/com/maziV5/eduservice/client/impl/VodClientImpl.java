package com.maziV5.eduservice.client.impl;

import com.maziV5.eduservice.client.VodClient;
import com.maziV5.utils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodClientImpl implements VodClient {
    @Override
    public R deleteVideo(String id) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteBatch(List<String> idList) {
        return R.error().message("删除多个视频出错");
    }
}
