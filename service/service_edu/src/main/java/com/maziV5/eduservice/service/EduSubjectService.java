package com.maziV5.eduservice.service;

import com.maziV5.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maziV5.eduservice.entity.Subject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author maziV5
 * @since 2023-03-29
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程分类
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    List<Subject> getAllSubject();
}
