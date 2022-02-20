package com.activeclub.fileserverminio.web.controller;

import com.activeclub.fileserverminio.bean.dto.FileOperationDto;
import com.activeclub.fileserverminio.core.annotation.recoredRequest.RequestRecord;
import com.activeclub.fileserverminio.core.bean.pojo.BaseResponse;
import com.activeclub.fileserverminio.core.web.BaseController;
import com.activeclub.fileserverminio.web.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@Api(tags = "api-v1实体文件操作")
@RequestMapping(value = "/api/v1/file")
public class FileController extends BaseController {

    @Autowired
    private FileService fileService;


    // http://127.0.0.1:40000/acfileserver/web/hello/sayHello
    @ApiOperation(value = "上传")
    @PostMapping("/upload")
    @ResponseBody
    public BaseResponse upload(@RequestParam("file") MultipartFile file,
                               FileOperationDto fileOperationDto) {
        return success("上传成功", fileService.upload(file, fileOperationDto));
    }

    @ApiOperation(value = "更新")
    @GetMapping("/update")
    @ResponseBody
    public BaseResponse update() {
        return success("更新成功");
    }

    @ApiOperation(value = "下载")
    @GetMapping("/download")
    @ResponseBody
    public BaseResponse download() {
        return success("下载成功");
    }

    @RequestRecord
    @ApiOperation(value = "预览")
    @GetMapping("/preview")
    @ResponseBody
    public void preview(String fileCode, HttpServletResponse res) {
        fileService.preview(fileCode, res);
    }

    @ApiOperation(value = "删除")
    @GetMapping("/delete")
    @ResponseBody
    public BaseResponse delete(String fileCode) {
        fileService.delete(fileCode);
        return success("删除成功");
    }

    @ApiOperation(value = "清理")
    @GetMapping("/clean")
    @ResponseBody
    public BaseResponse clean() {
        return success("清理成功");
    }
}