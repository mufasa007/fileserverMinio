package com.activeclub.fileserverminio.bean.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel("文件实体类")
@Data
public class File implements Serializable {

    @ApiModelProperty("自增id")
    private Long id;

    @ApiModelProperty("状态码：0正常存在、-id已删除、1有过期时间")
    private Long flag;

    @ApiModelProperty("唯一code")
    private String code;

    @ApiModelProperty("minio中的仓库名称")
    private String bucketName;

    @ApiModelProperty("minio中的仓库路径")
    private String path;

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

    @ApiModelProperty("过期时间")
    private Date overdueTime;

}
