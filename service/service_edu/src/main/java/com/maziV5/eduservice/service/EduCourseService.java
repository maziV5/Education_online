package com.maziV5.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maziV5.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maziV5.eduservice.entity.frontvo.CourseFrontVo;
import com.maziV5.eduservice.entity.frontvo.CourseWebVo;
import com.maziV5.eduservice.entity.vo.CourseInfoVo;
import com.maziV5.eduservice.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author maziV5
 * @since 2023-03-30
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String courseId);

    void deleteCourse(String courseId);

    Map<String, Object> getFrontCourseList(Page<EduCourse> page, CourseFrontVo courseFrontVo);

    CourseWebVo getCourseBaseInfo(String id);
}
