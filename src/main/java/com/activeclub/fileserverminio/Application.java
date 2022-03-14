package com.activeclub.fileserverminio;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@SentinelResource
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
