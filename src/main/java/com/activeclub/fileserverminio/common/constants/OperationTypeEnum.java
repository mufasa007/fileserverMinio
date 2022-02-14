package com.activeclub.fileserverminio.common.constants;


import static com.activeclub.fileserverminio.common.constants.NormalConstant.*;

public enum OperationTypeEnum {
    UPLOAD(UPLOAD_TYPE, "upload", "上传"),
    UPDATE(UPDATE_TYPE, "Update", "更新"),
    DOWNLOAD(DOWNLOAD_TYPE, "download", "下载"),
    PREVIEW(PREVIEW_TYPE, "preview", "预览"),
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
            default:
                return OTHER;
        }
    }

    private Short type;
    private String operationName;
    private String operationChnName;
    OperationTypeEnum(Short type, String operationName, String operationChnName) {
        this.type = type;
        this.operationName = operationName;
        this.operationChnName = operationChnName;
    }


}