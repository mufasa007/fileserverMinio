package com.activeclub.fileserverminio.core.utils;

import com.activeclub.fileserverminio.common.constants.OperationTypeEnum;
import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.core.util.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class RequestUtil {

    public Short getOperationType(String url) {
        if (!StringUtils.hasLength(url)) {
            return OperationTypeEnum.OTHER.type;
        } else if (url.contains(OperationTypeEnum.UPLOAD.operationName)) {
            return OperationTypeEnum.UPLOAD.type;
        } else if (url.contains(OperationTypeEnum.UPDATE.operationName)) {
            return OperationTypeEnum.UPDATE.type;
        } else if (url.contains(OperationTypeEnum.DOWNLOAD.operationName)) {
            return OperationTypeEnum.DOWNLOAD.type;
        } else if (url.contains(OperationTypeEnum.PREVIEW.operationName)) {
            return OperationTypeEnum.PREVIEW.type;
        } else if (url.contains(OperationTypeEnum.DELETE.operationName)) {
            return OperationTypeEnum.DELETE.type;
        } else if (url.contains(OperationTypeEnum.CLEAN.operationName)) {
            return OperationTypeEnum.CLEAN.type;
        } else {
            return OperationTypeEnum.OTHER.type;
        }
    }

    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    public String getFormParam(HttpServletRequest request) {
        MultipartResolver resolver = new StandardServletMultipartResolver();
        MultipartHttpServletRequest mRequest = resolver.resolveMultipart(request);

        Map<String, Object> param = new HashMap<>();
        Map<String, String[]> parameterMap = mRequest.getParameterMap();
        if (!parameterMap.isEmpty()) {
            param.putAll(parameterMap);
        }
        Map<String, MultipartFile> fileMap = mRequest.getFileMap();
        if (!fileMap.isEmpty()) {
            for (Map.Entry<String, MultipartFile> fileEntry : fileMap.entrySet()) {
                MultipartFile file = fileEntry.getValue();
                param.put(fileEntry.getKey(), file.getOriginalFilename() + "(" + file.getSize() + " byte)");
            }
        }
        return toJson(param);
    }

    public String getRequestBody(HttpServletRequest request) {
        int contentLength = request.getContentLength();
        if (contentLength <= 0) {
            return "";
        }
        try {
            return IOUtils.toString(request.getReader());
        } catch (IOException e) {
            log.error("获取请求体失败", e);
            return "";
        }
    }

    private String toJson(Object object) {
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
    }

}
