package com.activeclub.fileserverminio.common.constants;


import static com.activeclub.fileserverminio.common.constants.NormalConstant.*;

/**
 * 操作类型：上传、更新、下载、预览、其他
 */
public enum OperationTypeEnum {
    UPLOAD(UPLOAD_TYPE, "upload", "上传"),
    UPDATE(UPDATE_TYPE, "update", "更新"),
    DOWNLOAD(DOWNLOAD_TYPE, "download", "下载"),
    PREVIEW(PREVIEW_TYPE, "preview", "预览"),
    DELETE(DELETE_TYPE, "delete", "删除"),
    CLEAN(CLEAN_TYPE, "clean", "清理"),
    OTHER(OTHER_TYPE, "unknown", "未知");

    public OperationTypeEnum getByType(Short type) {
        switch (type) {
            case UPLOAD_TYPE:
                return UPLOAD;
            case UPDATE_TYPE:
                return UPDATE;
            case DOWNLOAD_TYPE:
                return DOWNLOAD;
            case PREVIEW_TYPE:
                return PREVIEW;
            case DELETE_TYPE:
                return DELETE;
            case CLEAN_TYPE:
                return CLEAN;
            default:
                return OTHER;
        }
    }

    public Short type;
    public String operationName;
    public String operationChnName;

    OperationTypeEnum(Short type, String operationName, String operationChnName) {
        this.type = type;
        this.operationName = operationName;
        this.operationChnName = operationChnName;
    }


}
