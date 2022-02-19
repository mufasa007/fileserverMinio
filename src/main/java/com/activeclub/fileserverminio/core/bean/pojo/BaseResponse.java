package com.activeclub.fileserverminio.core.bean.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse implements Serializable {

    private Object data;
    private String code;
    private String msg;

}
