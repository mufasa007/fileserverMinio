package com.activeclub.fileserverminio.core.annotation.recoredRequest;

import com.activeclub.fileserverminio.bean.pojo.Record;
import com.activeclub.fileserverminio.core.utils.RequestUtil;
import com.activeclub.fileserverminio.web.dao.RecordDao;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Objects;

@Log4j2
@Aspect
@Component
public class RecoredRequestAop {

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private RequestUtil requestUtil;

    @Pointcut("@annotation(com.activeclub.fileserverminio.core.annotation.recoredRequest.RequestRecord)")
    public void recordRequest() {

    }

    @Async
    @After("recordRequest()")
    public void doAfter() {
        Record record = new Record();
        try {
            HttpServletRequest request = ((ServletRequestAttributes)
                    Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            HttpServletResponse response = ((ServletRequestAttributes)
                    Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();

            long requestTime = System.currentTimeMillis();
            String requestIp = requestUtil.getIpAddr(request);
            String uri = request.getRequestURI();
            String contextPath = request.getContextPath();
            String url = uri.substring(contextPath.length());
            String method = request.getMethod();
            JSONObject json = new JSONObject();


            /**
             * get请求直接记录入参结束
             */
            if ("GET".equals(method)) {
                Enumeration<String> paraNames = request.getParameterNames();
                for (Enumeration<String> e = paraNames;
                     e.hasMoreElements(); ) {
                    String thisName = e.nextElement() + "";  //name名
                    String thisValue = request.getParameter(thisName);   //name名对应的值
                    json.put(thisName, thisValue);
                }
            } else {
                return;
            }

            record.setFileCode(json.get("fileCode").toString());
            record.setIp(requestIp);
            record.setType(requestUtil.getOperationType(uri));
            recordDao.insert(record);
        } catch (Exception e) {
            log.error("获取请求信息失败! ", e);
        }
    }
}

