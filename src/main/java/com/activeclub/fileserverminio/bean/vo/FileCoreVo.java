package com.activeclub.fileserverminio.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileCoreVo {

    @ApiModelProperty("唯一编码,32位")
    private String fileCode;

    @ApiModelProperty("预览(下载)链接")
    private String url;

}
