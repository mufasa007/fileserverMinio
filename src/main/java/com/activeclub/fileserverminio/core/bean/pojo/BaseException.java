package com.activeclub.fileserverminio.core.bean.pojo;

import lombok.Data;

@Data
public class BaseException extends RuntimeException {

    private String code;

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
    }
}
