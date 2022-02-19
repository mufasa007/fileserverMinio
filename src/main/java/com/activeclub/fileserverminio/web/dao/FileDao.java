package com.activeclub.fileserverminio.web.dao;

import com.activeclub.fileserverminio.bean.pojo.File;
import com.activeclub.fileserverminio.bean.vo.MinioCoreVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileDao {

    Integer upsert(File file);

    MinioCoreVo getMinioCoreVoByFileCode(String fileCode);

}
