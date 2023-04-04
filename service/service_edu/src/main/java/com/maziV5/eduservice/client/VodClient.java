package com.maziV5.eduservice.client;

import com.maziV5.eduservice.client.impl.VodClientImpl;
import com.maziV5.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod",fallback = VodClientImpl.class)
public interface VodClient {
    @DeleteMapping("/eduvod/video/deleteVideo/{id}")
    public R deleteVideo(@PathVariable("id") String id);

    @DeleteMapping("/eduvod/video/deleteBatch")
    public R deleteBatch(@RequestParam("idList") List<String> idList);
}
