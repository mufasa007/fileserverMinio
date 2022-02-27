package com.activeclub.fileserverminio.web.service.cache.impl;

import com.activeclub.fileserverminio.web.service.cache.CacheService;
import org.springframework.stereotype.Service;

@Service
public class RedisImpl implements CacheService {
    /**
     * 判断是否存在
     *
     * @param key 键值
     * @return boolean
     */
    @Override
    public boolean exists(String key) {
        return false;
    }

    /**
     * 获取数据
     *
     * @param key 键值
     * @return Object
     */
    @Override
    public Object get(String key) {
        return null;
    }

    /**
     * 新增数据
     *
     * @param key   键值
     * @param value
     */
    @Override
    public void put(String key, Object value) {

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
    public void put(String key, Object value, long expirateTime) {

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
