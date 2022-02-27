package com.activeclub.fileserverminio.web.service.file;

import com.activeclub.fileserverminio.bean.dto.FileOperationDto;
import com.activeclub.fileserverminio.bean.vo.FileCoreVo;
import io.minio.errors.*;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 文件操作接口
 */
public interface FileService {

    /**
     * 判断存储空间是否存在
     *
     */
    boolean doesBucketExist(String bucket);

    /**
     * 创建存储空间
     *
     */
    void createBucket(String bucket);

    /**
     * 上传文件
     *
     * @return 文件核心信息
     */
    String upload(MultipartFile file, FileOperationDto fileOperationDto);

    /**
     * 更新文件
     *
     * @return 文件核心信息
     */
    FileCoreVo update();

    /**
     * 下载文件
     *
     * @return 文件核心信息
     */
    void download();

    /**
     * 删除文件
     *
     * @return 文件核心信息
     */
    void preview(String fileCode, HttpServletResponse res);

    void delete(String fileCode);
}
