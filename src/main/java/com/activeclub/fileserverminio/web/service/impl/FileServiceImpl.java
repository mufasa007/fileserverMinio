package com.activeclub.fileserverminio.web.service.impl;

import com.activeclub.fileserverminio.bean.dto.FileOperationDto;
import com.activeclub.fileserverminio.bean.vo.FileCoreVo;
import com.activeclub.fileserverminio.bean.vo.MinioCoreVo;
import com.activeclub.fileserverminio.common.configs.DefaultConfig;
import com.activeclub.fileserverminio.common.constants.NormalConstant;
import com.activeclub.fileserverminio.common.constants.OptionCode;
import com.activeclub.fileserverminio.common.utils.FileUtil;
import com.activeclub.fileserverminio.core.bean.pojo.BaseException;
import com.activeclub.fileserverminio.web.dao.FileDao;
import com.activeclub.fileserverminio.web.service.FileService;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;


@Log4j2
@Service
public class FileServiceImpl implements InitializingBean, FileService {

    @Value("${minio.url:http://127.0.0.1:9000}")
    public String minioUrl;

    @Value("${minio.access.key:admin}")
    public String minioAccessKey;

    @Value("${minio.secre.key:activeclub}")
    public String minioSecreKey;

    public static MinioClient minioClient;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private DefaultConfig defaultConfig;

    @Autowired
    private FileDao fileDao;

    @Override
    public void afterPropertiesSet() {
        minioClient = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioAccessKey, minioSecreKey)
                .build();
    }

    /**
     * 上传文件
     *
     * @return 文件核心信息
     */
    @Override
    public FileCoreVo upload(MultipartFile file,
                             FileOperationDto fileOperationDto) {
        try {
            // 文件上传
            String fileName = fileUtil.fileRename(file);
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(fileOperationDto.getBucketName())
                    .object(fileName)
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(), file.getSize(), 0)
                    .build();
            minioClient.putObject(objectArgs);

        } catch (Exception e) {
            log.error("上传文件到minio仓库失败! ", e);
            throw new BaseException(OptionCode.UPLOAD_FILE_TO_MINIO_FAIL, "上传文件到minio仓库失败");
        }

        // 初始化数据
        String fileCode = UUID.randomUUID().toString().replace("-", "");
        String previewUrl = defaultConfig.getForeignIp() + ":" + defaultConfig.getForeignPort() +
                NormalConstant.preview_url_prefix + fileCode;

        FileCoreVo fileCoreVo = new FileCoreVo();
        fileCoreVo.setFileCode(fileCode);
        fileCoreVo.setPreviewUrl(previewUrl);

        // 数据库操作(异步) todo
        return fileCoreVo;

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
     * @return 文件核心信息
     */
    @Override
    public void preview(String fileCode, HttpServletResponse res) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // 通过fileCode查询文件核心信息
        MinioCoreVo minioCoreVo = fileDao.getMinioCoreVoByFileCode(fileCode);

        // 下载
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(minioCoreVo.getBucketName())
                .object(minioCoreVo.getFileName()).build();
        try (GetObjectResponse response = minioClient.getObject(objectArgs)) {
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
                while ((len = response.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                res.setCharacterEncoding("utf-8");
                //设置强制下载不打开
                //res.setContentType("application/force-download");
                res.addHeader("Content-Disposition", "attachment;fileName=" + minioCoreVo.getFileName());
                try (ServletOutputStream stream = res.getOutputStream()) {
                    stream.write(bytes);
                    stream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
