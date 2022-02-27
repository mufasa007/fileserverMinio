package com.activeclub.fileserverminio.web.service.qury.impl;

import com.activeclub.fileserverminio.bean.pojo.File;
import com.activeclub.fileserverminio.bean.pojo.FileRelation;
import com.activeclub.fileserverminio.bean.vo.FileAllInfoVo;
import com.activeclub.fileserverminio.bean.vo.MinioCoreVo;
import com.activeclub.fileserverminio.core.bean.pojo.BaseException;
import com.activeclub.fileserverminio.web.dao.FileDao;
import com.activeclub.fileserverminio.web.dao.FileRelationDao;
import com.activeclub.fileserverminio.web.service.cache.CacheService;
import com.activeclub.fileserverminio.web.service.qury.DbService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import static com.activeclub.fileserverminio.common.constants.OptionCode.PARAM_IS_NULL;
import static com.activeclub.fileserverminio.common.constants.OptionCode.SAVE_DATA_TO_DB_FAIL;

@Log4j2
@Service
public class DbServiceImpl implements DbService {

    @Autowired
    private FileDao fileDao;

    @Resource(name = "${cache.service:memoryImpl}")
    private CacheService cacheService;

    @Autowired
    private FileRelationDao fileRelationDao;

    /**
     * 通过文件code获取链接
     *
     * @param fileCode 文件code
     * @return 链接
     */
    @Override
    public String getUrlByFileCode(String fileCode) {
        if (!StringUtils.hasLength(fileCode)) {
            return null;
        }
        String md5 = (String) cacheService.getHotData(fileCode);
        if (!StringUtils.hasLength(md5)) {
            md5 = fileRelationDao.getMd5ByFileCode(fileCode);
        }
        if (!StringUtils.hasLength(md5)) {
            return null;
        } else {
            cacheService.putHotData(fileCode, md5);
            return getUrlByMd5(md5);
        }
    }

    /**
     * 通过文件md5码获取链接
     *
     * @param md5 文件md5
     * @return 链接
     */
    @Override
    public String getUrlByMd5(String md5) {
        if (!StringUtils.hasLength(md5)) {
            return null;
        }

        String url = (String) cacheService.getHotData(md5);
        if (!StringUtils.hasLength(url)) {
            url = fileDao.getUrlByMd5(md5);
        }
        if (!StringUtils.hasLength(url)) {
            return null;
        } else {
            cacheService.putHotData(md5, url);
            return url;
        }
    }

    /**
     * 新增修改操作
     *
     * @param file 文件详细信息
     */
    @Override
    public void upsertFileInfo(File file) {
        if (!StringUtils.hasLength(file.getMd5())) {
            throw new BaseException(PARAM_IS_NULL, "文件md5为空");
        }
        if (!StringUtils.hasLength(file.getFileName())) {
            throw new BaseException(PARAM_IS_NULL, "文件名称为空");
        }
        if (file.getSize() == null) {
            throw new BaseException(PARAM_IS_NULL, "文件大小值为空");
        }
        Integer result = 0;
        try {
            result = fileDao.upsert(file);
            if (result == 0) {
                throw new BaseException(SAVE_DATA_TO_DB_FAIL, "数据保存到数据库失败! ");
            }
        } catch (Exception e) {
            log.error("数据保存到数据库失败! ", e);
            throw new BaseException(SAVE_DATA_TO_DB_FAIL, "数据保存到数据库失败! ");
        }
    }

    /**
     * 通过文件code查询到minio的核心数据
     *
     * @param fileCode 文件code
     * @return MinioCoreVo
     */
    @Override
    public MinioCoreVo getMinioCoreVoByFileCode(String fileCode) {
        // todo 后续优化使用caffeine+redis缓存 热门数据

        MinioCoreVo minioCoreVo = fileDao.getMinioCoreVoByFileCode(fileCode);
        return minioCoreVo;
    }

    /**
     * 新增修改 虚拟与实体文件关系表
     *
     * @param fileRelation 文件关系信息
     */
    @Override
    public void upsertFileRelation(FileRelation fileRelation) {
        fileRelationDao.upsert(fileRelation);
    }

    /**
     * 通过文件code获取文件综合信息
     *
     * @param fileCode 文件code
     */
    @Override
    public FileAllInfoVo getFileAllInfoVoByFileCode(String fileCode) {
        return fileRelationDao.getFileAllInfoVoByFileCode(fileCode);
    }
}
