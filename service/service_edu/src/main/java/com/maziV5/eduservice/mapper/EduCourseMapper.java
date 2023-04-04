package com.maziV5.eduservice.mapper;

import com.maziV5.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maziV5.eduservice.entity.frontvo.CourseWebVo;
import com.maziV5.eduservice.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author maziV5
 * @since 2023-03-30
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getCourseBaseInfo(String id);
}
