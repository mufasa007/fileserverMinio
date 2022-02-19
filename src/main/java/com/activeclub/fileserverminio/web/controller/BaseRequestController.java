package com.activeclub.fileserverminio.web.controller;

import com.activeclub.fileserverminio.core.bean.pojo.BaseResponse;
import com.activeclub.fileserverminio.core.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "api-v1对外基础接口")
@RequestMapping(value = "/api/v1")
public class BaseRequestController extends BaseController {

    // http://127.0.0.1:40000/acfileserver/api/v1/healthCheck
    @ApiOperation(value = "查询组件是否正常运行")
    @GetMapping("/healthCheck")
    @ResponseBody
    public BaseResponse hello() {
        return success("查询结果成功", "up");
    }

}
