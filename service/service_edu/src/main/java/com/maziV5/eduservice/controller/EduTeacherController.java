package com.maziV5.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maziV5.eduservice.entity.EduTeacher;
import com.maziV5.eduservice.entity.vo.TeacherQuery;
import com.maziV5.eduservice.service.EduTeacherService;
import com.maziV5.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author maziV5
 * @since 2023-03-25
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;

    //1.查询讲师表所有数据
    @GetMapping("findAll")
    @ApiOperation(value = "所有讲师列表")
    public R findAllTeacher(){
        List<EduTeacher> teacherList = teacherService.list(null);
        return R.ok().data("teacherList",teacherList);
    }

    //2.根据ID逻辑讲师
    @DeleteMapping("{id}")
    @ApiOperation(value = "根据ID逻辑删除讲师")
    public R LogicDeleteTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        }
        return R.error();
    }

    //3.分页查询讲师
    @GetMapping("pageTeacher/{current}/{limit}")
    @ApiOperation(value = "分页查询讲师")
    public R pageListTeacher(@PathVariable long current, @PathVariable long limit){
        //创建page对象
        Page<EduTeacher> page = new Page<>(current,limit);
        //实现分页
        //调用方法时，底层会将数据封装到page对象中
        teacherService.page(page);

        //总记录数
        long total = page.getTotal();

        //数据list集合
        List<EduTeacher> records = page.getRecords();

        Map map = new HashMap();
        map.put("total",total);
        map.put("records",records);

        return R.ok().data(map);
    }

    //4.条件查询带分页
    @PostMapping ("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> page = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //判断条件值是否为空
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name",name);
        }

        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }

        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }

        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }

        //排序
        wrapper.orderByDesc("gmt_create");


        //实现分页
        teacherService.page(page,wrapper);

        //总记录数
        long total = page.getTotal();

        //数据list集合
        List<EduTeacher> records = page.getRecords();

        Map map = new HashMap();
        map.put("total",total);
        map.put("records",records);

        return R.ok().data(map);
    }

    //5.添加讲师
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        }
        return R.error();
    }

    //6.根据ID查询讲师
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable long id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("eduTeacher",eduTeacher);
    }

    //7.修改讲师
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        }
        return R.error();
    }

}

