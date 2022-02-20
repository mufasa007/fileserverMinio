package com.activeclub.fileserverminio.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MinioCoreVo {

    @ApiModelProperty("唯一编码,32位")
    private String fileCode;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件格式")
    private String format;

    @ApiModelProperty("minio中的仓库名称")
    private String minioBucket;

    @ApiModelProperty("minio中的仓库路径")
    private String minioPath;



}
