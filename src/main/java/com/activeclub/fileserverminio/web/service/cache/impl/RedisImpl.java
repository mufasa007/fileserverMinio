package com.activeclub.fileserverminio.web.service.cache.impl;

import com.activeclub.fileserverminio.web.service.cache.CacheService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Lazy
@Log4j2
@Service(value = "redisImpl")
public class RedisImpl implements CacheService {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    /**
     * 判断是否存在
     *
     * @param key 键值
     * @return boolean
     */
    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    private void test(){
//        redisTemplate.op
    }

    /**
     * 获取数据
     *
     * @param key 键值
     * @return Object
     */
    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 新增数据
     *
     * @param key   键值
     * @param value
     */
    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);
    }

    /**
     * 获取热点数据
     *
     * @param key 键值
     * @return Object
     */
    @Override
    public Object getHotData(String key) {
        return null;
    }

    /**
     * 新增热点数据
     *
     * @param key   键值
     * @param value
     */
    @Override
    public void putHotData(String key, Object value) {

    }

    /**
     * 新增会自动过期的数据
     *
     * @param key          键值
     * @param value
     * @param expirateTime
     */
    @Override
    public void set(String key, Object value, long expirateTime) {

    }

    /**
     * 删除数据
     *
     * @param key 键值
     */
    @Override
    public void del(String key) {

    }

}
