package com.activeclub.fileserverminio.web.service.file.impl;

import com.activeclub.fileserverminio.bean.dto.FileOperationDto;
import com.activeclub.fileserverminio.bean.vo.FileCoreVo;
import com.activeclub.fileserverminio.common.utils.DateUtil;
import com.activeclub.fileserverminio.core.bean.pojo.BaseException;
import com.activeclub.fileserverminio.web.service.file.FileService;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Lazy
@Log4j2
@Service(value = "fileAliyunOssImpl")
public class FileAliyunOssImpl implements InitializingBean, FileService {

    @Value("${endpoint:yourEndpoint}")
    private String endpoint;

    @Value("${access.key.id:yourAccessKeyId}")
    private String accessKeyId;

    @Value("${access.key.secret:yourAccessKeySecret}")
    private String accessKeySecret = "yourAccessKeySecret";

    private static OSS ossClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 判断存储空间是否存在
     *
     * @param bucket
     */
    @Override
    public boolean doesBucketExist(String bucket) {
        try {
            return ossClient.doesBucketExist(bucket);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException("0000", "查看存储空间是否存在失败! ");
        }
    }

    /**
     * 创建存储空间
     *
     * @param bucket 存储空间名称
     */
    @Override
    public void createBucket(String bucket) {
        try {
            if (!doesBucketExist(bucket)) {
                // 创建存储空间。
                ossClient.createBucket(bucket);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException("0000", "创建存储空间失败! ");
        }
    }

    /**
     * 上传文件
     *
     * @param file
     * @param fileOperationDto
     * @return 文件核心信息
     */
    @Override
    public String upload(MultipartFile file, FileOperationDto fileOperationDto) {
        String bucket = fileOperationDto.getBucket();
        String path = fileOperationDto.getPath() + "/" + file.getOriginalFilename();

        try {
            createBucket(bucket);
            ossClient.putObject(bucket, path, file.getInputStream());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException("0000", "上传文件失败! ");
        }

        try {
            return ossClient.generatePresignedUrl(bucket, path, DateUtil.getMaxOverdueTime()).toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException("0000", "获取下载链接失败! ");
        }
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

}
