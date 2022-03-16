package com.activeclub.fileserverminio.web.service.cache.impl;

import com.activeclub.fileserverminio.web.service.cache.CacheService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Lazy
@Log4j2
@Service(value = "redisImpl")
public class RedisImpl implements CacheService {

    /** 释放锁lua脚本 */
    private static final String RELEASE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";


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

    @Override
    public boolean getLock(String key, long timeSeconds) {
        return redisTemplate.opsForValue().setIfAbsent(key, "lock", timeSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean getLock(String key) {
        return redisTemplate.opsForValue().setIfAbsent(key, "lock", 300, TimeUnit.SECONDS);
    }

    @Override
    public boolean releaseLock(String key) {
        Object lockValue = redisTemplate.opsForValue().get(key);
        if(lockValue == null){
            return true;
        }

        String value = lockValue.toString();
        Object[] objects = new Object[]{value};
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_LUA_SCRIPT,Long.class);

        Long result =  (Long)redisTemplate.execute(redisScript, Collections.singletonList(key),objects);
        if(result==1L){
            return true;
        }else {
            return false;
        }
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
