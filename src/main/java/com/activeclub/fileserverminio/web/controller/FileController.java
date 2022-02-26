package com.activeclub.fileserverminio.web.controller;

import com.activeclub.fileserverminio.bean.dto.FileOperationDto;
import com.activeclub.fileserverminio.core.annotation.recoredRequest.RequestRecord;
import com.activeclub.fileserverminio.core.bean.pojo.BaseResponse;
import com.activeclub.fileserverminio.core.web.BaseController;
import com.activeclub.fileserverminio.web.service.file.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@Api(tags = "api-v1实体文件操作")
@RequestMapping(value = "/api/v1/file")
public class FileController extends BaseController {

    @Resource(name="${fileserver.impl:fileAliyunOssImpl}")
    private FileService fileService;

    @ApiOperation(value = "上传",position = 1)
    @PostMapping("/upload")
    @ResponseBody
    public BaseResponse upload(@RequestParam("file") MultipartFile file,
                               FileOperationDto fileOperationDto) {
        return success("上传成功", fileService.upload(file, fileOperationDto));
    }

    @ApiOperation(value = "批量上传",position = 2)
    @PostMapping("/uploadBatch")
    @ResponseBody
    public BaseResponse uploadBatch(@RequestParam("file") MultipartFile file,
                               FileOperationDto fileOperationDto) {
        return success("上传成功", fileService.upload(file, fileOperationDto));
    }

    @ApiOperation(value = "更新",position = 3)
    @PostMapping("/update")
    @ResponseBody
    public BaseResponse update(@RequestParam("fileCode") String fileCode,
                               @RequestParam("file") MultipartFile file) {
        return success("更新成功");
    }

    @ApiOperation(value = "批量更新",position = 4)
    @PostMapping("/updateBatch")
    @ResponseBody
    public BaseResponse updateBatch(@RequestParam("fileCodeList") List<String> fileCodeList,
                                    @RequestParam("fileList") List<MultipartFile> fileList) {
        return success("更新成功");
    }

    @ApiOperation(value = "下载",position = 5)
    @GetMapping("/download")
    @ResponseBody
    public BaseResponse download(@RequestParam("fileCode") String fileCode) {
        return success("下载成功");
    }

    @ApiOperation(value = "Get批量下载",position = 6)
    @GetMapping("/downloadBatch")
    @PostMapping("/downloadBatch")
    @ResponseBody
    public BaseResponse downloadBatchByGet(String fileCodeList) {
        return success("批量下载");
    }

    @RequestRecord
    @ApiOperation(value = "预览",position = 7)
    @GetMapping("/preview")
    @ResponseBody
    public void preview(String fileCode, HttpServletResponse res) {
        fileService.preview(fileCode, res);
    }

    @ApiOperation(value = "删除",position = 8)
    @GetMapping("/delete")
    @ResponseBody
    public BaseResponse delete(@RequestParam("fileCode") String fileCode) {
        fileService.delete(fileCode);
        return success("删除成功");
    }

    @ApiOperation(value = "批量删除",position = 9)
    @GetMapping("/deleteBatch")
    @ResponseBody
    public BaseResponse deleteBatchByGet(String fileCodeList) {
//        fileService.deleteBatch(fileCodeList);
        return success("批量删除");
    }

    @ApiOperation(value = "清理",position = 10)
    @GetMapping("/clean")
    @ResponseBody
    public BaseResponse clean() {
        return success("清理成功");
    }
}