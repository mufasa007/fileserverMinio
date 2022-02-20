package com.activeclub.fileserverminio.common.utils;

import com.activeclub.fileserverminio.bean.vo.MinioCoreVo;
import com.activeclub.fileserverminio.core.bean.pojo.BaseException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Objects;

import static com.activeclub.fileserverminio.common.constants.OptionCode.GET_DATA_FAIL;

@Log4j2
@Component
public class FileUtil {


    public void onlinePreview(MinioCoreVo minioCoreVo, HttpServletResponse res) {



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
