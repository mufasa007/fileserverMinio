package com.activeclub.fileserverminio.bean.vo;

import com.activeclub.fileserverminio.bean.pojo.FileRelation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ApiModel("文件综合信息类")
public class FileAllInfoVo extends FileRelation {

    @ApiModelProperty("下载链接")
    private String url;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件格式")
    private String format;

    @ApiModelProperty("文件大小")
    private Long size;

}
