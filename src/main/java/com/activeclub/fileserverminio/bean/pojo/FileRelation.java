package com.activeclub.fileserverminio.bean.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@ApiModel("虚拟文件与实体文件关系类")
public class FileRelation {

    @ApiModelProperty("自增id")
    private Long id;

    @ApiModelProperty("状态码：0正常存在、-id已删除、1有过期时间")
    private Long flag;

    /**
     * 对外的唯一文件code，联合文件索引系统
     */
    @ApiModelProperty("唯一编码:32位")
    private String fileCode;

    /* minio核心数据 */
    @ApiModelProperty("仓库名称")
    private String bucket;

    @ApiModelProperty("文件路径")
    private String path;

    /**
     * 实体文件code
     */
    @ApiModelProperty("md5码:核心数据")
    private String md5;

    @ApiModelProperty("创建人账户名")
    private String creatorAccountName;

    @ApiModelProperty("修改人账户名")
    private String updateAccountName;

    @ApiModelProperty("创建日期")
    private Date createTime;

    @ApiModelProperty("修改日期")
    private Date updateTime;

    @ApiModelProperty("过期日期")
    private Date overdueTime;

}
