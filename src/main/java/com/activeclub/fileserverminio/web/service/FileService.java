package com.activeclub.fileserverminio.web.service;

import com.activeclub.fileserverminio.bean.dto.FileOperationDto;
import com.activeclub.fileserverminio.bean.vo.FileCoreVo;
import io.minio.errors.*;
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
     * 上传文件
     *
     * @return 文件核心信息
     */
    FileCoreVo upload(MultipartFile file, FileOperationDto fileOperationDto);

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
    void preview(String fileCode, HttpServletResponse res) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

}
