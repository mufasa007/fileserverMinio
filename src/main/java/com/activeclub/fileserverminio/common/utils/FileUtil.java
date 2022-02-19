package com.activeclub.fileserverminio.common.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Objects;

@Log4j2
@Component
public class FileUtil {

    public String getMd5(MultipartFile file) {
        try {
            byte[] uploadBytes = file.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            return new BigInteger(1, digest).toString(16);
        } catch (Exception e) {
            log.error("获取md5码失败", e);
        }
        return null;
    }

    public String fileRename(MultipartFile file) {
        // 获取原文件名 + 时间戳(System.currentTimeMillis())，作为上传文件的文件名
        String[] circleSp = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        //形成新的文件名
        return circleSp[0] + System.currentTimeMillis() + "." + circleSp[1];
    }

}
