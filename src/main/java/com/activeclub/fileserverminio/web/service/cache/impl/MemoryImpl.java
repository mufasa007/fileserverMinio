//package com.activeclub.fileserverminio.web.service.cache.impl;
//
//import com.activeclub.fileserverminio.web.service.cache.CacheService;
//import com.google.common.collect.Maps;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@Lazy
//@Log4j2
//@Service(value = "memoryImpl")
//public class MemoryImpl implements CacheService {
//
//    private Map<String, Object> cache =
//            Maps.newHashMapWithExpectedSize(1024);
//
//    /**
//     * 判断是否存在
//     *
//     * @param key 键值
//     * @return boolean
//     */
//    @Override
//    public boolean exists(String key) {
//        return cache.containsKey(key);
//    }
//
//    /**
//     * 获取数据
//     *
//     * @param key 键值
//     * @return Object
//     */
//    @Override
//    public Object get(String key) {
//        return cache.get(key);
//    }
//
//    /**
//     * 新增数据
//     *
//     * @param key 键值
//     */
//    @Override
//    public String set(String key, String value) {
//        cache.put(key, value);
//        return null;
//    }
//
//    /**
//     * 获取热点数据
//     * todo 优化
//     *
//     * @param key 键值
//     * @return Object
//     */
//    @Override
//    public Object getHotData(String key) {
//        return cache.get(key);
//    }
//
//    /**
//     * 新增热点数据
//     * todo 优化
//     *
//     * @param key 键值
//     */
//    @Override
//    public void putHotData(String key, Object value) {
//        cache.put(key, value);
//    }
//
//    /**
//     * 新增会自动过期的数据
//     *
//     * @param key          键值
//     * @param value
//     * @param expirateTime
//     */
//    @Override
//    public void set(String key, Object value, long expirateTime) {
//
//    }
//
//    /**
//     * 删除数据
//     *
//     * @param key 键值
//     */
//    @Override
//    public void del(String key) {
//        cache.remove(key);
//    }
//}
