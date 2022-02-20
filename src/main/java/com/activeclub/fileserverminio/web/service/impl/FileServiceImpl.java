package com.activeclub.fileserverminio.web.service.impl;

import com.activeclub.fileserverminio.bean.dto.FileOperationDto;
import com.activeclub.fileserverminio.bean.pojo.File;
import com.activeclub.fileserverminio.bean.vo.FileCoreVo;
import com.activeclub.fileserverminio.bean.vo.MinioCoreVo;
import com.activeclub.fileserverminio.common.configs.DefaultConfig;
import com.activeclub.fileserverminio.common.constants.NormalConstant;
import com.activeclub.fileserverminio.common.constants.OptionCode;
import com.activeclub.fileserverminio.common.utils.DateUtil;
import com.activeclub.fileserverminio.common.utils.FileUtil;
import com.activeclub.fileserverminio.core.bean.pojo.BaseException;
import com.activeclub.fileserverminio.core.utils.SessionService;
import com.activeclub.fileserverminio.web.service.DbService;
import com.activeclub.fileserverminio.web.service.FileService;
import io.minio.*;
import io.minio.errors.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;

import static com.activeclub.fileserverminio.common.constants.OptionCode.GET_FILE_DATA_FAIL;
import static com.activeclub.fileserverminio.common.constants.OptionCode.PARAM_IS_NULL;


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
    private DbService dbService;

    @Autowired
    private SessionService sessionService;

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
    public FileCoreVo upload(MultipartFile multipartFile,
                             FileOperationDto fileOperationDto) {

        // todo 后续使用md5进行联合文件索引（借鉴docker的Union Filesystem）
        String md5Code = fileUtil.getMd5(multipartFile);

        // 参数解析
        String fileName = multipartFile.getOriginalFilename();
        String[] circleSp = Objects.requireNonNull(fileName).split("\\.");
        String fileNameOnly = circleSp[0];
        String format = "";
        String minioPath = "";
        if (circleSp.length >= 2) {
            format = circleSp[1];
            minioPath = fileOperationDto.getMinioPath() + "/" + fileNameOnly + "(" + DateUtil.getPreTime() + ")." + format;
        } else {
            minioPath = fileOperationDto.getMinioPath() + "/" + fileNameOnly + "(" + DateUtil.getPreTime() + ")";
        }

        // 调用minio上传文件
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(fileOperationDto.getMinioBucket())
                    .object(minioPath)
                    .contentType(multipartFile.getContentType())
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(), 0)
                    .build();
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            log.error("上传文件到minio仓库失败! ", e);
            throw new BaseException(OptionCode.UPLOAD_FILE_TO_MINIO_FAIL, "上传文件到minio仓库失败");
        }

        // 初始化返回数据

        String fileCode = UUID.randomUUID().toString().replace("-", "");
        String previewUrl = defaultConfig.getForeignIp() + ":" + defaultConfig.getForeignPort()
                + defaultConfig.getServerServletContextPath() + NormalConstant.preview_url_prefix + fileCode;

        FileCoreVo fileCoreVo = new FileCoreVo();
        fileCoreVo.setFileCode(fileCode);
        fileCoreVo.setPreviewUrl(previewUrl);


        // 将数据存储到数据库
        File file = new File();
        file.setFileCode(fileCode);
        file.setFileName(fileName);
        file.setFormat(format);
        file.setPreviewUrl(previewUrl);

        file.setMinioBucket(fileOperationDto.getMinioBucket());
        file.setMinioPath(minioPath);

        file.setMd5Code(md5Code);
        file.setSize(multipartFile.getSize());
        file.setCreatorAccountName(sessionService.getPreUser());
        // 数据库操作(异步) todo
        dbService.upsertFileInfo(file);

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
    public void preview(String fileCode, HttpServletResponse res) {
        // 通过fileCode查询文件核心信息
        MinioCoreVo minioCoreVo = dbService.getMinioCoreVoByFileCode(fileCode);
        if (ObjectUtils.isEmpty(minioCoreVo)) {
            throw new BaseException(PARAM_IS_NULL, "查询该文件信息为空");
        }

        // 调用minio获取文件流
        InputStream inputStream = fileUtil.getObject(minioCoreVo.getMinioBucket(), minioCoreVo.getMinioPath());

        // 预览操作
        fileUtil.onlinePreview(minioCoreVo,inputStream,res);
    }

}
