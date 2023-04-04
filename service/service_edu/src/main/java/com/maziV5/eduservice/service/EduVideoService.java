package com.maziV5.eduservice.service;

import com.maziV5.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程小节（课程视频） 服务类
 * </p>
 *
 * @author maziV5
 * @since 2023-03-30
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideoByCourseId(String courseId);

    void deleteById(String id);
}
