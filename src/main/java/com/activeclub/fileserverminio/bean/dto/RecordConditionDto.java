package com.activeclub.fileserverminio.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RecordConditionDto implements Serializable {

    @ApiModelProperty("文件code:唯一码")
    private String fileCode;

    @ApiModelProperty("操作人ip")
    private String ip;

    @ApiModelProperty("操作类型")
    private Short type;

    @ApiModelProperty("请求链接")
    private String requestUrl;

    @ApiModelProperty("操作时间点之前")
    private Date beforeTime;

    @ApiModelProperty("操作时间点之后")
    private Date afterTime;

}
