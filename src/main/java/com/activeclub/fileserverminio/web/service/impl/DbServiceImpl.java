package com.activeclub.fileserverminio.web.service.impl;

import com.activeclub.fileserverminio.bean.vo.MinioCoreVo;
import com.activeclub.fileserverminio.web.dao.FileDao;
import com.activeclub.fileserverminio.web.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbServiceImpl implements DbService {

    @Autowired
    private FileDao fileDao;

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
