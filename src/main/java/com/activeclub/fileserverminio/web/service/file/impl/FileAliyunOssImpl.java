package com.activeclub.fileserverminio.web.service.file.impl;

import com.activeclub.fileserverminio.bean.dto.FileOperationDto;
import com.activeclub.fileserverminio.bean.vo.FileCoreVo;
import com.activeclub.fileserverminio.web.service.file.FileService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Lazy
@Service(value = "fileAliyunOssImpl")
public class FileAliyunOssImpl implements InitializingBean, FileService {

    /**
     * 上传文件
     *
     * @param file
     * @param fileOperationDto
     * @return 文件核心信息
     */
    @Override
    public FileCoreVo upload(MultipartFile file, FileOperationDto fileOperationDto) {
        return null;
    }

    /**
     * 更新文件
     *
     * @return 文件核心信息
     */
    @Override
    public FileCoreVo update() {
        return null;
    }

    /**
     * 下载文件
     *
     * @return 文件核心信息
     */
    @Override
    public void download() {

    }

    /**
     * 删除文件
     *
     * @param fileCode
     * @param res
     * @return 文件核心信息
     */
    @Override
    public void preview(String fileCode, HttpServletResponse res) {

    }

    @Override
    public void delete(String fileCode) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
