package com.activeclub.fileserverminio.core.handler;

import com.activeclub.fileserverminio.core.bean.pojo.BaseException;
import com.activeclub.fileserverminio.core.bean.pojo.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.activeclub.fileserverminio.common.constants.OptionCode.UNKOWN_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse baseExceptionHandler(Exception e) {
        BaseResponse baseResponse = new BaseResponse();
        if (e instanceof BaseException) {
            BaseException be = (BaseException) e;
            baseResponse.setCode(be.getCode());
            baseResponse.setMsg(be.getMessage());
        } else {
            e.printStackTrace();
            baseResponse.setCode(UNKOWN_ERROR);
            baseResponse.setMsg(e.getMessage());
        }

        return baseResponse;
    }
}