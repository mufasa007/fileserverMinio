package com.activeclub.fileserverminio.web.service.operate;

import com.activeclub.fileserverminio.bean.dto.FileOperationDto;
import com.activeclub.fileserverminio.bean.pojo.File;
import com.activeclub.fileserverminio.bean.vo.FileCoreVo;
import com.activeclub.fileserverminio.common.utils.FileUtil;
import com.activeclub.fileserverminio.core.utils.RandomUtil;
import com.activeclub.fileserverminio.core.utils.SessionService;
import com.activeclub.fileserverminio.web.service.cache.CacheService;
import com.activeclub.fileserverminio.web.service.file.FileService;
import com.activeclub.fileserverminio.web.service.qury.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OperateImpl implements Operate {

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private CacheService cacheService;

    @Resource(name = "fileAliyunOssImpl")
    private FileService fileService;

    @Autowired
    private DbService dbService;

    @Autowired
    private SessionService sessionService;

    /**
     * 上传文件
     *
     * @param multipartFile 文件流
     * @param fileOperationDto 文件基础信息
     * @return 文件核心信息
     */
    @Override
    public FileCoreVo upload(MultipartFile multipartFile, FileOperationDto fileOperationDto) {
        // 获取文件md5码
        String md5 = fileUtil.getMd5(multipartFile);
        String fileCode = RandomUtil.getUuid();
        String fileName = multipartFile.getOriginalFilename();
        String format = fileUtil.getFileFormat(fileName);
        Long size = multipartFile.getSize();
        String creatorAccountName = sessionService.getPreAccountName(fileOperationDto.getAccountName());
        String url = "";

        // 通过md5码查询是否已有相关数据
        Object objUrl = cacheService.getHotData(md5);
        if (!ObjectUtils.isEmpty(objUrl)) {
            // 已有历史缓存数据
            url = objUrl.toString();
        } else {
            // 暂无历史数据,直接上传
            url = fileService.upload(multipartFile, fileOperationDto);
            // 将数据链接缓存
            cacheService.putHotData(md5, url);
        }

        // 存储
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

        // 反参
        return FileCoreVo.builder()
                .fileCode(fileCode)
                .url(url)
                .build();
    }

    /**
     * 批量上传文件
     *
     * @param file
     * @param fileOperationDto
     * @return 文件核心信息
     */
    @Override
    public List<FileCoreVo> uploadBatch(List<MultipartFile> file, FileOperationDto fileOperationDto) {
        return null;
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
        return null;
    }

    /**
     * 批量更新文件
     *
     * @param fileList
     * @param fileCodeList
     * @return 文件核心信息
     */
    @Override
    public FileCoreVo updateBatch(List<MultipartFile> fileList, List<String> fileCodeList) {
        return null;
    }

    /**
     * 下载文件
     *
     * @param fileCode
     */
    @Override
    public void download(String fileCode) {

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
