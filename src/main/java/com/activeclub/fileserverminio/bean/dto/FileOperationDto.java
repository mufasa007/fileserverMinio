package com.activeclub.fileserverminio.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class FileOperationDto {

    @ApiModelProperty(value = "minio中的仓库名称(必填)")
    @NotBlank(message = "数据桶名称不能为空! ")
    private String minioBucket;

    @ApiModelProperty("minio中的仓库路径(必填)")
    @NotBlank(message = "数据存储路径不能为空! ")
    private String minioPath;

    @ApiModelProperty("创建人名称(非必填)")
    private String accountName;

}
