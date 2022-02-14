package com.activeclub.fileserverminio.web.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "文件下载")
@RequestMapping(value = "/web/download")
public class DownloadController {

    // http://127.0.0.1:40000/acfileserver/web/hello/sayHello
    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return "hello world! ";
    }

}
