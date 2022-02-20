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
        Long size = multipartFile.getSize();

        String creatorAccountName = sessionService.getPreUser();
        String fileCode = UUID.randomUUID().toString().replace("-", "");
        String previewUrl = defaultConfig.getForeignIp() + ":" + defaultConfig.getForeignPort() +
                NormalConstant.preview_url_prefix + fileCode;

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
        file.setSize(size);
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
    public ResponseEntity<Object> preview(String fileCode, HttpServletResponse res) {
        // 通过fileCode查询文件核心信息
        MinioCoreVo minioCoreVo = dbService.getMinioCoreVoByFileCode(fileCode);
        if (ObjectUtils.isEmpty(minioCoreVo)) {
            throw new BaseException(PARAM_IS_NULL, "查询该文件code数据为空");
        }

        StatObjectArgs statObjectArgs = StatObjectArgs.builder()
                .bucket(minioCoreVo.getMinioBucket())
                .object(minioCoreVo.getMinioPath())
                .build();

        try {
            minioClient.statObject(statObjectArgs);
            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket(minioCoreVo.getMinioBucket())
                    .object(minioCoreVo.getMinioPath())
                    .build();
            InputStream inputStream = minioClient.getObject(getObjectArgs);

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
            OutputStream outputStream = res.getOutputStream();
            res.setContentType("image/jpeg");
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
        return null;
    }

}
