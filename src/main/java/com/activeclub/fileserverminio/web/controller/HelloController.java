package com.activeclub.fileserverminio.web.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "健康检查")
@RequestMapping(value = "/web/hello")
public class HelloController {

    // http://127.0.0.1:40000/acfileserver/web/hello/sayHello
    @GetMapping("/sayHello")
    @ResponseBody
    public String hello() {
        return "helloWorld";
    }

}
