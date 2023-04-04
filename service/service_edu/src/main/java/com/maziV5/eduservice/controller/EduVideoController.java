package com.maziV5.eduservice.controller;


import com.maziV5.eduservice.entity.EduVideo;
import com.maziV5.eduservice.service.EduVideoService;
import com.maziV5.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程小节（课程视频） 前端控制器
 * </p>
 *
 * @author maziV5
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    EduVideoService videoService;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    //删除小节时，同时删除小节的视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        videoService.deleteById(id);
        return R.ok();
    }

    //根据id查询小节
    @GetMapping("{videoId}")
    public R getVideoById(@PathVariable String videoId){
        EduVideo eduVideo = videoService.getById(videoId);

        return R.ok().data("video",eduVideo);
    }

    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);

        return R.ok();
    }
}

