package com.activeclub.fileserverminio.common.constants;

public interface NormalConstant {

    // 操作类型
    short OTHER_TYPE = -1;
    short UPLOAD_TYPE = 0;
    short UPDATE_TYPE = 1;
    short DOWNLOAD_TYPE = 2;
    short PREVIEW_TYPE = 3;
    short DELETE_TYPE = 4;
    short CLEAN_TYPE = 5;

    // 反参处理
    String preview_url_prefix = "/api/v1/file/preview?fileCode=";

}
