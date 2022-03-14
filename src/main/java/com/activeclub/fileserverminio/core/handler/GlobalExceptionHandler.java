package com.activeclub.fileserverminio.core.handler;

import com.activeclub.fileserverminio.core.bean.pojo.BaseException;
import com.activeclub.fileserverminio.core.bean.pojo.BaseResponse;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.UndeclaredThrowableException;

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
        } else if (e instanceof UndeclaredThrowableException && ((UndeclaredThrowableException) e).getUndeclaredThrowable() instanceof FlowException) {
            e.printStackTrace();
            baseResponse.setCode(UNKOWN_ERROR);
            baseResponse.setMsg("sentinel限流");
        } else {
            e.printStackTrace();
            baseResponse.setCode(UNKOWN_ERROR);
            baseResponse.setMsg(e.getMessage());
        }

        return baseResponse;
    }
}