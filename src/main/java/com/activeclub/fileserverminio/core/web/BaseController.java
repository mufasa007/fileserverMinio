package com.activeclub.fileserverminio.core.web;

import com.activeclub.fileserverminio.core.bean.pojo.BaseResponse;

/**
 * 基础controller辅助类
 */
public class BaseController {

    public BaseResponse success(String msg) {
        return success(msg, null);
    }

    public BaseResponse success(String msg, Object data) {
        BaseResponse br = new BaseResponse();
        br.setMsg(msg);
        br.setCode("0");
        br.setData(data);
        return br;
    }
}
