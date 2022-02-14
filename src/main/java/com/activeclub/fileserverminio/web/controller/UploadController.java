package com.activeclub.fileserverminio.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "文件上传")
@RequestMapping(value = "/web/upload")
public class UploadController {

    // http://127.0.0.1:40000/acfileserver/web/hello/sayHello
    @ApiOperation(value = "文件上传")
    @GetMapping("/sayHello")
    @ResponseBody
    public String hello() {
        return "hello world! ";
    }

}
