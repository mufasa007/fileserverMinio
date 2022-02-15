package com.activeclub.fileserverminio.bean.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("文件实体类")
public class File implements Serializable {

    @ApiModelProperty("自增id")
    private Long id;

    @ApiModelProperty("状态码：0正常存在、-id已删除、1有过期时间")
    private Long flag;

    @ApiModelProperty("md5码:唯一编码,32位")
    private String code;

    @ApiModelProperty("文件名称")
    private String fileName;


    @ApiModelProperty("minio中的仓库名称")
    private String bucketName;

    @ApiModelProperty("minio中的仓库路径")
    private String path;

    @ApiModelProperty("文件大小")
    private Long size;

    @ApiModelProperty("被阅览的次数")
    private Long viewFrequency;



    @ApiModelProperty("创建人名称")
    private String creatorAccountName;

    @ApiModelProperty("修改人名称")
    private String updateAccountName;

    @ApiModelProperty("创建日期")
    private Date createTime;

    @ApiModelProperty("修改日期")
    private Date updateTime;

    @ApiModelProperty("过期日期")
    private Date overdueTime;

}
