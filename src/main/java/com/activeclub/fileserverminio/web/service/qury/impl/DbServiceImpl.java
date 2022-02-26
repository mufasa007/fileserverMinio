package com.activeclub.fileserverminio.web.service.qury.impl;

import com.activeclub.fileserverminio.bean.pojo.File;
import com.activeclub.fileserverminio.bean.vo.MinioCoreVo;
import com.activeclub.fileserverminio.core.bean.pojo.BaseException;
import com.activeclub.fileserverminio.web.dao.FileDao;
import com.activeclub.fileserverminio.web.service.qury.DbService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.activeclub.fileserverminio.common.constants.OptionCode.PARAM_IS_NULL;
import static com.activeclub.fileserverminio.common.constants.OptionCode.SAVE_DATA_TO_DB_FAIL;

@Log4j2
@Service
public class DbServiceImpl implements DbService {

    @Autowired
    private FileDao fileDao;

    /**
     * 新增修改操作
     *
     * @param file 文件详细信息
     */
    @Override
    public void upsertFileInfo(File file) {
        if (!StringUtils.hasLength(file.getFileCode())) {
            throw new BaseException(PARAM_IS_NULL, "文件code为空");
        }
        if (!StringUtils.hasLength(file.getFileName())) {
            throw new BaseException(PARAM_IS_NULL, "文件名称为空");
        }
        if (!StringUtils.hasLength(file.getMd5Code())) {
            throw new BaseException(PARAM_IS_NULL, "文件md5码为空");
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
            log.error("数据保存到数据库失败! ",e);
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
}
