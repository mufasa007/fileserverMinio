package com.activeclub.fileserverminio.common.utils;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Log4j2
public class MinioUtil implements InitializingBean {

    private static MinioClient minioClient;

    @Value("${minio.url:http://127.0.0.1:9000}")
    private String minioUrl;

    @Value("${minio.access.key:admin}")
    private String minioAccessKey;

    @Value("${minio.secre.key:activeclub}")
    private String minioSecreKey;

    @Override
    public void afterPropertiesSet() {
        minioClient = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioAccessKey, minioSecreKey)
                .build();
    }

    /**
     * 文件上传
     *
     * @param bucketName 存储桶名称
     * @param path       路径
     * @param fileName   文件名称
     */
    public void uploadFile(String bucketName, String path, String fileName) {
        log.info("upload file [{}] ready ! ", fileName);
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {
                System.out.println("Bucket 'asiatrip' already exists.");
            }

            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .filename(path)
                            .build());
            log.info("upload file [{}] success ! ", fileName);
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            log.error(String.format("upload file [%s] broken down ! exception :{}", fileName), e);
        }
    }


}
