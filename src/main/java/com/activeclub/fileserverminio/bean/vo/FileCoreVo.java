package com.activeclub.fileserverminio.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FileCoreVo {

    @ApiModelProperty("唯一编码,32位")
    private String fileCode;

    @ApiModelProperty("预览链接")
    private String previewUrl;

}
