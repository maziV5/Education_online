package com.maziV5.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maziV5.eduservice.entity.EduCourse;
import com.maziV5.eduservice.entity.EduTeacher;
import com.maziV5.eduservice.service.EduCourseService;
import com.maziV5.eduservice.service.EduTeacherService;
import com.maziV5.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    @GetMapping("getTeacherFront/{current}/{limit}")
    public R getTeacherFrontList(@PathVariable long current,@PathVariable long limit){
        Page<EduTeacher> page = new Page<>(current,limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(page);

        return R.ok().data("map",map);
    }

    //讲师详情
    @GetMapping("getTeacherFrontInfo/{id}")
    public R getTeacherFrontInfo(@PathVariable String id){
        //根据id查询讲师
        EduTeacher eduTeacher = teacherService.getById(id);

        //根据id查询所讲课程
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",id);

        List<EduCourse> courseList = courseService.list(queryWrapper);

        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);

    }
}
