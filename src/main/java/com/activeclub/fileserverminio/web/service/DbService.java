package com.activeclub.fileserverminio.web.service;

import com.activeclub.fileserverminio.bean.vo.MinioCoreVo;

public interface DbService {

    /**
     * 通过文件code查询到minio的核心数据
     * @param fileCode 文件code
     * @return MinioCoreVo
     */
    MinioCoreVo getMinioCoreVoByFileCode(String fileCode);

}
