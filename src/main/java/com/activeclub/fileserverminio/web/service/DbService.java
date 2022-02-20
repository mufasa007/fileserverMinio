package com.activeclub.fileserverminio.web.service;

import com.activeclub.fileserverminio.bean.pojo.File;
import com.activeclub.fileserverminio.bean.vo.MinioCoreVo;

public interface DbService {

    /**
     * 新增修改操作
     * @param file 文件详细信息
     */
    void upsertFileInfo(File file);

    /**
     * 通过文件code查询到minio的核心数据
     * @param fileCode 文件code
     * @return MinioCoreVo
     */
    MinioCoreVo getMinioCoreVoByFileCode(String fileCode);

}
