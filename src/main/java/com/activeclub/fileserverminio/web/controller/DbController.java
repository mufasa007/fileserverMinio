package com.activeclub.fileserverminio.web.controller;

import com.activeclub.fileserverminio.core.bean.pojo.BaseResponse;
import com.activeclub.fileserverminio.core.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "api-v1查询信息接口")
@RequestMapping(value = "/web/info")
public class DbController extends BaseController {

    // http://127.0.0.1:40000/acfileserver/web/hello/sayHello
    @GetMapping("/getInfoByFileCode")
    public BaseResponse getInfoByFileCode(
            @ApiParam(value = "fileCode",required = true) String fileCode) {
        return success("查询文件信息成功",fileCode);
    }

}
