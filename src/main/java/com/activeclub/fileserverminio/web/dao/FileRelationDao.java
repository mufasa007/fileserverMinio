package com.activeclub.fileserverminio.web.dao;

import com.activeclub.fileserverminio.bean.pojo.File;
import com.activeclub.fileserverminio.bean.pojo.FileRelation;
import com.activeclub.fileserverminio.bean.vo.FileAllInfoVo;
import com.activeclub.fileserverminio.bean.vo.MinioCoreVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileRelationDao {

    /**
     * 更新修改
     * @param fileRelation
     * @return
     */
    Integer upsert(FileRelation fileRelation);


    FileAllInfoVo getFileAllInfoVoByFileCode(String fileCode);

    /**
     * 通过文件code获取文件md5码
     *
     * @param fileCode 文件code
     * @return 文件md5码
     */
    String getMd5ByFileCode(String fileCode);

}
