package com.activeclub.fileserverminio.web.service.qury;

import com.activeclub.fileserverminio.bean.pojo.File;
import com.activeclub.fileserverminio.bean.pojo.FileRelation;
import com.activeclub.fileserverminio.bean.vo.FileAllInfoVo;
import com.activeclub.fileserverminio.bean.vo.MinioCoreVo;

public interface DbService {

    /**
     * 通过文件code获取链接
     *
     * @param fileCode 文件code
     * @return 链接
     */
    String getUrlByFileCode(String fileCode);

    /**
     * 通过文件md5码获取链接
     *
     * @param md5 文件md5
     * @return 链接
     */
    String getUrlByMd5(String md5);

    /**
     * 新增修改操作
     *
     * @param file 文件详细信息
     */
    void upsertFileInfo(File file);

    /**
     * 通过文件code查询到minio的核心数据
     *
     * @param fileCode 文件code
     * @return MinioCoreVo
     */
    MinioCoreVo getMinioCoreVoByFileCode(String fileCode);

    /**
     * 新增修改 虚拟与实体文件关系表
     *
     * @param fileRelation 文件关系信息
     */
    void upsertFileRelation(FileRelation fileRelation);

    /**
     * 通过文件code获取文件综合信息
     *
     * @param fileCode 文件code
     */
    FileAllInfoVo getFileAllInfoVoByFileCode(String fileCode);
}
