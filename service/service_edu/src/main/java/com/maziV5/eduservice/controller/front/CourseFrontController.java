package com.maziV5.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maziV5.eduservice.entity.EduCourse;
import com.maziV5.eduservice.entity.chapter.ChapterVo;
import com.maziV5.eduservice.entity.frontvo.CourseFrontVo;
import com.maziV5.eduservice.entity.frontvo.CourseWebVo;
import com.maziV5.eduservice.service.EduChapterService;
import com.maziV5.eduservice.service.EduCourseService;
import com.maziV5.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @PostMapping("getFrontCourseList/{current}/{limit}")
    public R getFrontCourseList(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> page = new Page<>(current,limit);
        Map<String,Object> map = courseService.getFrontCourseList(page,courseFrontVo);

        return R.ok().data("map",map);
    }

    //课程详情
    @GetMapping("getFrontCourseInfo/{id}")
    public R getFrontCourseInfo(@PathVariable String id){
        CourseWebVo courseWebVo = courseService.getCourseBaseInfo(id);
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseId(id);

        return R.ok().data("courseWebVo",courseWebVo).data("chapterVoList",chapterVoList);
    }
}
