package com.activeclub.fileserverminio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class RedisTests {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Test
    void contextLoads() {
        System.out.println("hello");
//        redisTemplate.opsForValue().set("name","wanyu");
//        System.out.println(redisTemplate.opsForValue().get("name"));
    }

}
