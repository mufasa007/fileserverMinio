package com.activeclub.fileserverminio.common.utils;

import com.activeclub.fileserverminio.bean.vo.MinioCoreVo;
import com.activeclub.fileserverminio.core.bean.pojo.BaseException;
import io.minio.GetObjectArgs;
import io.minio.StatObjectArgs;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Objects;

import static com.activeclub.fileserverminio.common.constants.OptionCode.*;
import static com.activeclub.fileserverminio.web.service.impl.FileServiceImpl.minioClient;

@Log4j2
@Component
public class FileUtil {

    /**
     * 调用minio获取文件流
     *
     * @param minioBucket 文件minio桶信息
     * @param minioPath   文件minio路径信息
     * @return 文件流
     */
    public InputStream getObject(String minioBucket, String minioPath) {
        // 校验数据
        if (!StringUtils.hasLength(minioBucket)) {
            throw new BaseException(PARAM_IS_NULL, "文件minio桶信息为空! ");
        }
        if (!StringUtils.hasLength(minioPath)) {
            throw new BaseException(PARAM_IS_NULL, "文件minio路径信息为空! ");
        }

        // 调用minio获取文件流
        try {
            StatObjectArgs statObjectArgs = StatObjectArgs.builder()
                    .bucket(minioBucket)
                    .object(minioPath)
                    .build();
            minioClient.statObject(statObjectArgs);
            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket(minioBucket)
                    .object(minioPath)
                    .build();
            return minioClient.getObject(getObjectArgs);
        } catch (Exception e) {
            log.error("调用minio服务获取文件流失败! ", e);
            throw new BaseException(GET_FILE_DATA_FAIL, "调用minio服务获取文件流失败! ");
        }
    }

    /**
     * 预览文件操作
     *
     * @param minioCoreVo 文件核心数据
     * @param inputStream 文件流
     * @param response    返回体
     */
    public void onlinePreview(MinioCoreVo minioCoreVo, InputStream inputStream, HttpServletResponse response) {

        try {
            //获取文件类型
            String suffix = minioCoreVo.getFormat();
            if (!suffix.equals("txt")
                    && !suffix.equals("doc") &&
                    !suffix.equals("docx") &&
                    !suffix.equals("xls") &&
                    !suffix.equals("xlsx") &&
                    !suffix.equals("ppt") &&
                    !suffix.equals("jpg") &&
                    !suffix.equals("pptx")) {
                throw new Exception("文件格式不支持预览");
            }
            OutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            //创建存放文件内容的数组
            byte[] buff =new byte[1024];
            //所读取的内容使用n来接收
            int n;
            //当没有读取完时,继续读取,循环
            while((n=inputStream.read(buff))!=-1){
                //将字节数组的数据全部写入到输出流中
                outputStream.write(buff,0,n);
            }
            //强制将缓存区的数据进行输出
            outputStream.flush();
            //关流
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            log.error("获取文件数据失败! ", e);
            throw new BaseException(GET_FILE_DATA_FAIL, "获取文件数据失败! ");
        }
    }


    /**
     * 获取文件md5码 todo 后续优化性能
     *
     * @param file 文件
     * @return
     */
    public String getMd5(MultipartFile file) {
        try {
            byte[] uploadBytes = file.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            return new BigInteger(1, digest).toString(16);
        } catch (Exception e) {
            log.error("获取md5码失败", e);
            throw new BaseException(GET_DATA_FAIL, "获取md5码失败");
        }
    }

    public String fileRename(MultipartFile file) {
        // 获取原文件名 + 时间戳(System.currentTimeMillis())，作为上传文件的文件名
        String[] circleSp = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        //形成新的文件名
        return circleSp[0] + System.currentTimeMillis() + "." + circleSp[1];
    }

    public String getFileFormat(MultipartFile file) {
        // 获取原文件名 + 文件类型
        String[] circleSp = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        if (circleSp.length >= 2) {
            return circleSp[1];
        } else {
            return null;
        }
    }


}
