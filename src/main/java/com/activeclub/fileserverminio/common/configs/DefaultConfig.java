package com.activeclub.fileserverminio.common.configs;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class DefaultConfig implements InitializingBean {

    /**
     * 对外开放的ip和端口
     */
    @Value("${foreign.ip:http://127.0.0.1}")
    private String foreignIp;

    @Value("${foreign.port:40000}")
    private String foreignPort;

    @Value("${server.servlet.context-path:/acfileserver}")
    private String serverServletContextPath;

    @Override
    public void afterPropertiesSet() {

    }
}
