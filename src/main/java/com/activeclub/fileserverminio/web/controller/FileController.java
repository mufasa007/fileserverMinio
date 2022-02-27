package com.activeclub.fileserverminio.web.controller;

import com.activeclub.fileserverminio.bean.dto.FileOperationDto;
import com.activeclub.fileserverminio.core.annotation.recoredRequest.RequestRecord;
import com.activeclub.fileserverminio.core.bean.pojo.BaseResponse;
import com.activeclub.fileserverminio.core.web.BaseController;
import com.activeclub.fileserverminio.web.service.operate.Operate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@Api(tags = "api-v1实体文件操作")
@RequestMapping(value = "/api/v1/file")
public class FileController extends BaseController {

    @Autowired
    private Operate operate;

    @ApiOperation(value = "上传")
    @PostMapping("/upload")
    @ResponseBody
    public BaseResponse upload(@RequestParam(value = "file", required = true) MultipartFile file,
                               FileOperationDto fileOperationDto) {
        return success("上传成功", operate.upload(file, fileOperationDto));
    }

    @ApiOperation(value = "批量上传")
    @PostMapping("/uploadBatch")
    @ResponseBody
    public BaseResponse uploadBatch(@RequestParam(value = "fileList", required = true) List<MultipartFile> fileList,
                                    @RequestBody FileOperationDto fileOperationDto) {
        return success("批量上传成功", operate.uploadBatch(fileList, fileOperationDto));
    }

    @ApiOperation(value = "更新")
    @PostMapping("/update")
    @ResponseBody
    public BaseResponse update(@RequestParam(value = "file", required = true) MultipartFile file,
                               @RequestParam(value = "fileCode", required = true) String fileCode) {
        return success("更新成功", operate.update(file, fileCode));
    }

    @ApiOperation(value = "批量更新")
    @PostMapping("/updateBatch")
    @ResponseBody
    public BaseResponse updateBatch(@RequestParam(value = "fileList", required = true) List<MultipartFile> fileList,
                                    @RequestParam(value = "fileCodeList", required = true) List<String> fileCodeList) {
        return success("更新成功", operate.updateBatch(fileList, fileCodeList));
    }

    @ApiOperation(value = "下载")
    @GetMapping("/download")
    @ResponseBody
    public BaseResponse download(@RequestParam(value = "fileCode", required = true) String fileCode
            , HttpServletResponse response) {
        operate.download(fileCode,response);
        return success("下载成功");
    }

    @ApiOperation(value = "批量下载")
    @GetMapping("/downloadBatch")
    @PostMapping("/downloadBatch")
    @ResponseBody
    public BaseResponse downloadBatch(@RequestParam(value = "fileCodeList", required = true) List<String> fileCodeList) {
        operate.downloadBatch(fileCodeList);
        return success("批量下载");
    }

    @RequestRecord
    @ApiOperation(value = "预览")
    @GetMapping("/preview")
    @ResponseBody
    public BaseResponse preview(@RequestParam(value = "fileCode", required = true) String fileCode) {
        operate.preview(fileCode);
        return success("预览");
    }

    @ApiOperation(value = "删除")
    @GetMapping("/delete")
    @ResponseBody
    public BaseResponse delete(@RequestParam(value = "fileCode", required = true) String fileCode) {
        operate.delete(fileCode);
        return success("删除成功");
    }

    @ApiOperation(value = "批量删除")
    @GetMapping("/deleteBatch")
    @ResponseBody
    public BaseResponse deleteBatch(@RequestParam(value = "fileCodeList", required = true) List<String> fileCodeList) {
        operate.deleteBatch(fileCodeList);
        return success("批量删除");
    }

    @ApiOperation(value = "清理")
    @GetMapping("/clean")
    @ResponseBody
    public BaseResponse clean() {
        operate.clean();
        return success("清理成功");
    }
}