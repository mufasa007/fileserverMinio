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
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

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
     * ????????????
     *
     * @return ??????????????????
     */
    @Override
    public FileCoreVo upload(MultipartFile multipartFile,
                             FileOperationDto fileOperationDto) {

        // todo ????????????md5?????????????????????????????????docker???Union Filesystem???
        String md5Code = fileUtil.getMd5(multipartFile);

        // ????????????
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

        // ??????minio????????????
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(fileOperationDto.getMinioBucket())
                    .object(minioPath)
                    .contentType(multipartFile.getContentType())
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(), 0)
                    .build();
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            log.error("???????????????minio????????????! ", e);
            throw new BaseException(OptionCode.UPLOAD_FILE_TO_MINIO_FAIL, "???????????????minio????????????");
        }

        // ?????????????????????

        String fileCode = UUID.randomUUID().toString().replace("-", "");
        String previewUrl = defaultConfig.getForeignIp() + ":" + defaultConfig.getForeignPort()
                + defaultConfig.getServerServletContextPath() + NormalConstant.preview_url_prefix + fileCode;

        FileCoreVo fileCoreVo = new FileCoreVo();
        fileCoreVo.setFileCode(fileCode);
        fileCoreVo.setPreviewUrl(previewUrl);


        // ???????????????????????????
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
        // ???????????????(??????) todo
        dbService.upsertFileInfo(file);

        return fileCoreVo;

    }

    /**
     * ????????????
     *
     * @return ??????????????????
     */
    @Override
    public FileCoreVo update() {
        return null;
    }

    /**
     * ????????????
     *
     * @return ??????????????????
     */
    @Override
    public void download() {

    }

    /**
     * ????????????
     *
     * @param fileCode
     * @return ??????????????????
     */
    @Override
    public void preview(String fileCode, HttpServletResponse res) {
        // ??????fileCode????????????????????????
        MinioCoreVo minioCoreVo = dbService.getMinioCoreVoByFileCode(fileCode);
        if (ObjectUtils.isEmpty(minioCoreVo)) {
            throw new BaseException(PARAM_IS_NULL, "???????????????????????????");
        }

        // ??????minio???????????????
        InputStream inputStream = fileUtil.getObject(minioCoreVo.getMinioBucket(), minioCoreVo.getMinioPath());

        // ????????????
        fileUtil.onlinePreview(minioCoreVo, inputStream, res);
    }

    @Override
    public void delete(String fileCode) {

    }

}
