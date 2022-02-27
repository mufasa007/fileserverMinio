package com.activeclub.fileserverminio.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class FileOperationDto {

    @ApiModelProperty(value = "数据桶名称(必填)")
    @NotBlank(message = "数据桶名称不能为空! ")
    private String bucket;

    @ApiModelProperty("数据存储路径(必填)")
    @NotBlank(message = "数据存储路径不能为空! ")
    private String path;

    @ApiModelProperty("创建人名称(非必填)")
    private String accountName;

}
