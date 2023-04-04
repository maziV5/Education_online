package com.maziV5.controller;

import com.maziV5.service.VodService;
import com.maziV5.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    //上传视频
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file){
        String videoId = vodService.uploadVideo(file);

        return R.ok().data("videoId",videoId);
    }

    //删除视频
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        vodService.deleteVideo(id);

        return R.ok();

    }

    //删除多个视频
    //参数多个视频ID
    @DeleteMapping("deleteBatch")
    public R deleteBatch(@RequestParam("idList") List idList){
        vodService.deleteMoreVideo(idList);

        return R.ok();
    }

}
