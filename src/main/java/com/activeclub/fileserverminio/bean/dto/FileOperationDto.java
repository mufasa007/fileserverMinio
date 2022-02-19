package com.activeclub.fileserverminio.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FileOperationDto {

    @ApiModelProperty(value = "minio中的仓库名称")
    @NotBlank(message = "数据桶名称不能为空! ")
    private String bucketName;

    @ApiModelProperty("minio中的仓库路径")
    @NotBlank(message = "数据存储路径不能为空! ")
    private String path;

    @ApiModelProperty("创建人名称")
    private String accountName;

}
