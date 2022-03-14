package com.activeclub.fileserverminio.web.controller;

import com.activeclub.fileserverminio.core.bean.pojo.BaseException;
import com.activeclub.fileserverminio.core.bean.pojo.BaseResponse;
import com.activeclub.fileserverminio.core.web.BaseController;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "api-v1测试接口类")
@RequestMapping(value = "/test/v1")
public class TestRequestController extends BaseController {

    // http://127.0.0.1:40000/acfileserver/api/v1/healthCheck
    @ApiOperation(value = "sentinel限流测试")
    @GetMapping("/sentinel")
    @ResponseBody
    @SentinelResource
    public String sentinel(String str) {
        if (!StringUtils.hasLength(str)) {
            return "输入参数为空";
        }

//        if ("error".equals(str)) {
//            throw new BaseException("0001", "测试失败! ");
//        }

        return str;
    }

}
