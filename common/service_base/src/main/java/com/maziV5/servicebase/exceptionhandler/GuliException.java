package com.maziV5.servicebase.exceptionhandler;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException{
    //状态码
    private Integer code;
    //异常信息
    private String msg;
}
