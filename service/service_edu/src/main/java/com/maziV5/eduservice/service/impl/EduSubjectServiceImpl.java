package com.maziV5.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maziV5.eduservice.entity.EduSubject;
import com.maziV5.eduservice.entity.Subject;
import com.maziV5.eduservice.entity.excel.SubjectData;
import com.maziV5.eduservice.listener.SubjectExcelListener;
import com.maziV5.eduservice.mapper.EduSubjectMapper;
import com.maziV5.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author maziV5
 * @since 2023-03-29
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            InputStream fileInputStream = file.getInputStream();

            EasyExcel.read(fileInputStream, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //课程分类列表（树形）
    @Override
    public List<Subject> getAllSubject() {
        ArrayList<Subject> finalList = new ArrayList<>();

        //查询一级分类
        QueryWrapper<EduSubject> queryOne = new QueryWrapper<>();
        queryOne.eq("parent_id",0);
        List<EduSubject> parentList = this.list(queryOne);

        //查询二级分类
        QueryWrapper<EduSubject> queryTwo = new QueryWrapper<>();
        queryTwo.ne("parent_id",0);
        List<EduSubject> childrenList = this.list(queryTwo);

        //封装
        for (EduSubject eduSubject : parentList) {
            Subject parentSubject = new Subject();

            BeanUtils.copyProperties(eduSubject,parentSubject);

            List<Subject> list = new ArrayList<>();
            for (EduSubject childrenEduSubject : childrenList) {
                Subject childrenSubject = new Subject();

                if (childrenEduSubject.getParentId().equals(eduSubject.getId())){
                    BeanUtils.copyProperties(childrenEduSubject,childrenSubject);
                    list.add(childrenSubject);
                }

            }

            parentSubject.setChildren(list);

            finalList.add(parentSubject);
        }

        return finalList;
    }
}
