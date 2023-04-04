package com.maziV5.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maziV5.eduservice.client.VodClient;
import com.maziV5.eduservice.entity.EduVideo;
import com.maziV5.eduservice.mapper.EduVideoMapper;
import com.maziV5.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程小节（课程视频） 服务实现类
 * </p>
 *
 * @author maziV5
 * @since 2023-03-30
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;


    //根据课程id删除小节
    // 删除对应的视频文件
    @Override
    public void removeVideoByCourseId(String courseId) {


        //根据课程id查询所有视频id
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);

        List<EduVideo> eduVideoList = this.list(queryWrapper);
        ArrayList<String> ids = new ArrayList<>();
        for (EduVideo eduVideo : eduVideoList) {
            String videoSourceId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                ids.add(eduVideo.getVideoSourceId());
            }
        }

        if (ids.size() > 0) {
            vodClient.deleteBatch(ids);
        }

        this.remove(queryWrapper);
    }

    //根据id删除小节
    @Override
    public void deleteById(String id) {
        //根据小节id得到视频id
        EduVideo eduVideo = this.getById(id);
        System.out.println(eduVideo);
        String videoSourceId = eduVideo.getVideoSourceId();

        //根据视频id，远程调用实现视频删除
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodClient.deleteVideo(videoSourceId);
        }

        this.removeById(id);

    }
}
