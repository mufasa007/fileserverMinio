package com.activeclub.fileserverminio.web.controller;

import com.activeclub.fileserverminio.core.bean.pojo.BaseResponse;
import com.activeclub.fileserverminio.core.web.BaseController;
import com.activeclub.fileserverminio.web.service.cache.CacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "api-v1查询信息接口")
@RequestMapping(value = "/web/cache")
public class CacheController extends BaseController {

    @Resource(name = "${cache.service:redisImpl}")
    private CacheService cacheService;

    // http://127.0.0.1:40000/acfileserver/web/hello/sayHello
    @GetMapping("/set")
    public BaseResponse set(String key, String value) {
        cacheService.set(key, value);
        return success("查询文件信息成功");
    }

    @GetMapping("/get")
    public BaseResponse get(String key) {
        return success("查询文件信息成功",cacheService.get(key));
    }

}
