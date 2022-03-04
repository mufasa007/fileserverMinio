package com.activeclub.fileserverminio.web.service.operate;

import com.activeclub.fileserverminio.bean.dto.FileOperationDto;
import com.activeclub.fileserverminio.bean.pojo.File;
import com.activeclub.fileserverminio.bean.pojo.FileRelation;
import com.activeclub.fileserverminio.bean.vo.FileAllInfoVo;
import com.activeclub.fileserverminio.bean.vo.FileCoreVo;
import com.activeclub.fileserverminio.common.utils.FileUtil;
import com.activeclub.fileserverminio.common.utils.ListUtil;
import com.activeclub.fileserverminio.core.bean.pojo.BaseException;
import com.activeclub.fileserverminio.core.utils.RandomUtil;
import com.activeclub.fileserverminio.core.utils.SessionService;
import com.activeclub.fileserverminio.web.service.cache.CacheService;
import com.activeclub.fileserverminio.web.service.file.FileService;
import com.activeclub.fileserverminio.web.service.qury.DbService;
import lombok.extern.log4j.Log4j2;
import org.bouncycastle.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

@Log4j2
@Service
public class OperateImpl implements Operate {

    @Autowired
    private FileUtil fileUtil;

    @Resource(name = "${cache.service:redisImpl}")
    private CacheService cacheService;

    @Resource(name = "${file.service:fileAliyunOssImpl}")
    private FileService fileService;

    @Autowired
    private DbService dbService;

    @Autowired
    private SessionService sessionService;

    /**
     * 上传文件
     *
     * @param multipartFile    文件流
     * @param fileOperationDto 文件基础信息
     * @return 文件核心信息
     */
    @Override
    public FileCoreVo upload(MultipartFile multipartFile, FileOperationDto fileOperationDto) {
        // 获取文件md5码
        String fileCode = RandomUtil.getUuid();
        String md5 = fileUtil.getMd5(multipartFile);
        String url = dbService.getUrlByMd5(md5);
        String creatorAccountName = sessionService.getPreAccountName(fileOperationDto.getAccountName());

        if (!StringUtils.hasLength(url)) {
            fileCode = RandomUtil.getUuid();
            String fileName = multipartFile.getOriginalFilename();
            String format = fileUtil.getFileFormat(fileName);
            Long size = multipartFile.getSize();
            // 暂无历史数据,直接上传
            url = fileService.upload(multipartFile, fileOperationDto);
            // 将数据链接缓存
            cacheService.putHotData(fileCode, md5);
            cacheService.putHotData(md5, url);
            // 存储数据库
            File file = File.builder()
                    .md5(md5)
                    .url(url)
                    .bucket(fileOperationDto.getBucket())
                    .path(fileOperationDto.getPath())
                    .fileName(fileName)
                    .format(format)
                    .size(size)
                    .creatorAccountName(creatorAccountName)
                    .build();
            dbService.upsertFileInfo(file);
        } else {
            // 已有历史数据，则需要直接copy操作，检查将原有数据置为公共数据
            // todo

        }

        // 将虚拟文件存储数据库
        FileRelation fileRelation = FileRelation.builder()
                .fileCode(fileCode)
                .md5(md5)
                .creatorAccountName(creatorAccountName)
                .bucket(fileOperationDto.getBucket())
                .path(fileOperationDto.getPath())
                .build();
        dbService.upsertFileRelation(fileRelation);

        // 反参
        return FileCoreVo.builder()
                .fileCode(fileCode)
                .url(url)
                .build();
    }

    /**
     * 批量上传文件
     *
     * @param multipartFileList 实体文件List
     * @param fileOperationDto  文件信息
     * @return 文件核心信息
     */
    @Override
    public List<FileCoreVo> uploadBatch(List<MultipartFile> multipartFileList, FileOperationDto fileOperationDto) {
        List<FileCoreVo> fileCoreVoList = new CopyOnWriteArrayList<>();
        multipartFileList.forEach(multipartFile -> fileCoreVoList.add(upload(multipartFile, fileOperationDto)));
        return fileCoreVoList;
    }

    /**
     * 更新文件
     *
     * @param file
     * @param fileCode
     * @return 文件核心信息
     */
    @Override
    public FileCoreVo update(MultipartFile file, String fileCode) {
        FileAllInfoVo fileAllInfoVoByFileCode = dbService.getFileAllInfoVoByFileCode(fileCode);
        if (fileAllInfoVoByFileCode == null) {
            throw new BaseException("9999", "通过文件code查询信息为空! ");
        }

        return upload(file, FileOperationDto.builder()
                .accountName(sessionService.getPreAccountName())
                .bucket(fileAllInfoVoByFileCode.getBucket())
                .path(fileAllInfoVoByFileCode.getPath())
                .build());
    }

    /**
     * 批量更新文件
     *
     * @param fileList     实体文件List
     * @param fileCodeList 文件codeList
     * @return 文件核心信息
     */
    @Override
    public List<FileCoreVo> updateBatch(List<MultipartFile> fileList, List<String> fileCodeList) {
        if (ListUtil.isEmpty(fileList)) {
            throw new BaseException("9999", "文件流不能为空! ");
        }
        if (ListUtil.isEmpty(fileCodeList)) {
            throw new BaseException("9999", "文件code不能为空! ");
        }
        if (fileList.size() != fileCodeList.size()) {
            throw new BaseException("9999", "文件流个数与文件code个数不一致! ");
        }
        List<FileCoreVo> fileCoreVoList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < fileList.size(); i++) {
            fileCoreVoList.add(update(fileList.get(i), fileCodeList.get(i)));
        }
        return fileCoreVoList;
    }

    /**
     * 下载文件
     *
     * @param fileCode 文件code
     */
    @Override
    public void download(String fileCode,HttpServletResponse response) {
        String url = dbService.getUrlByFileCode(fileCode);
        if (!StringUtils.hasLength(url)) {
            throw new BaseException("9999", "无法查询到该文件code的信息! ");
        }

        try {
            response.sendRedirect(url);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException("9999", "重定向失败! ");
        }
    }

    /**
     * 批量下载文件
     *
     * @param fileCodeList List<文件code>
     */
    @Override
    public void downloadBatch(List<String> fileCodeList) {

    }

    /**
     * 预览
     *
     * @param fileCode 文件code
     */
    @Override
    public void preview(String fileCode) {

    }

    /**
     * 删除文件:软删除
     *
     * @param fileCode 文件code
     */
    @Override
    public void delete(String fileCode) {

    }

    /**
     * 批量删除:软删除
     *
     * @param fileCodeList List<文件code>
     */
    @Override
    public void deleteBatch(List<String> fileCodeList) {

    }

    /**
     * 清理软删除数据
     */
    @Override
    public void clean() {

    }
}
