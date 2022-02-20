package com.activeclub.fileserverminio.web.dao;

import com.activeclub.fileserverminio.bean.dto.RecordConditionDto;
import com.activeclub.fileserverminio.bean.pojo.File;
import com.activeclub.fileserverminio.bean.pojo.Record;
import com.activeclub.fileserverminio.bean.vo.MinioCoreVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RecordDao {

    Integer insert(Record record);

    List<Record> getByCondition(RecordConditionDto recordConditionDto);

}
