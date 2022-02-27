package com.activeclub.fileserverminio.bean.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@ApiModel("文件实体类")
public class File implements Serializable {


    @ApiModelProperty("自增id")
    private Long id;

    @ApiModelProperty("状态码：0正常存在、-id已删除、1有过期时间")
    private Long flag;

    /* 数据库核心数据 */
    @ApiModelProperty("md5码:核心数据(唯一码)")
    private String md5;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("下载链接")
    private String url;

    /* minio核心数据 */
    @ApiModelProperty("仓库名称")
    private String bucket;

    @ApiModelProperty("文件路径")
    private String path;

    @ApiModelProperty("文件格式")
    private String format;

    @ApiModelProperty("文件大小")
    private Long size;

    @ApiModelProperty("被阅览的次数")
    private Long viewFrequency;

    @ApiModelProperty("初始创建人名称")
    private String creatorAccountName;

    @ApiModelProperty("初始创建日期")
    private Date createTime;

}
