package com.maziV5.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maziV5.eduservice.entity.EduCourse;
import com.maziV5.eduservice.entity.EduTeacher;
import com.maziV5.eduservice.service.EduCourseService;
import com.maziV5.eduservice.service.EduTeacherService;
import com.maziV5.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {
    @Autowired
    EduCourseService courseService;

    @Autowired
    EduTeacherService teacherService;

    //查询前8条热门课程，查询前4条讲师
    @GetMapping("index")
    public R index(){
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("view_count");
        queryWrapper.last("limit 8");
        List<EduCourse> courseList = courseService.list(queryWrapper);

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(wrapper);

        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
