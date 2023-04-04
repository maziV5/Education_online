package com.maziV5.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maziV5.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author maziV5
 * @since 2023-03-25
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> page);
}
