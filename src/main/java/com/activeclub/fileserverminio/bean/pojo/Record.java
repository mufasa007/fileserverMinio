package com.activeclub.fileserverminio.bean.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件的整个生命周期记录
 */
@Data
@ApiModel("操作记录实体类")
public class Record implements Serializable {

    @ApiModelProperty("自增id")
    private Long id;

    @ApiModelProperty("文件code:唯一码")
    private String fileCode;

    @ApiModelProperty("操作人ip")
    private String ip;

    @ApiModelProperty("操作类型")
    private Short type;

    @ApiModelProperty("操作时间点")
    private Date momentTime;

}
