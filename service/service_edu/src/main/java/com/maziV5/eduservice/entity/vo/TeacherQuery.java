package com.maziV5.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class TeacherQuery {
    @ApiModelProperty(value = "讲师名称，模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间", example = "2023-1-1 12:00:00")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2023-1-1 12:00:00")
    private String end;
}
