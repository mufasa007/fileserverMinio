package com.activeclub.fileserverminio.bean.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RecordPageDto extends RecordConditionDto implements Serializable {

    private Integer pageSize;
    private Integer pageNum;
    private Integer total;
    private List<?> list;

}
