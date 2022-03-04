//package com.activeclub.fileserverminio.web.service.cache.impl;
//
//import com.activeclub.fileserverminio.web.service.cache.CacheService;
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Service;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.Transaction;
//
//import java.util.List;
//
//@Lazy
//@Log4j2
//@Service(value = "jedisImpl")
//public class JedisImpl implements CacheService, InitializingBean {
//
//    @Value("${redis.ip:127.0.0.1}")
//    private String redisIp;
//
//    @Value("${redis.port:6379}")
//    private Integer redisPort;
//
//    @Value("${redis.auth:123456}")
//    private String redisAuth;
//
//    Jedis jedis;
//
//    @Override
//    public void afterPropertiesSet() {
//        jedis = new Jedis(redisIp, redisPort);
//        log.info(jedis.auth(redisAuth));
//        log.info(jedis.ping());
//        jedis.watch("k1");
//        Transaction multi = jedis.multi();
//        try {
//            multi.watch("k1");
//            multi.set("k1","v1");
//            multi.set("k2","v2");
//            int i = 1/0;
//            log.info(multi.get("k1"));
//            log.info(JSONObject.toJSONString(multi.exec()));
//        }catch (Exception e){
//            multi.discard();
//            multi.unwatch();
//            log.info("jedis 事务失败! ");
//        }
//    }
//
//    /**
//     * 判断是否存在
//     *
//     * @param key 键值
//     * @return boolean
//     */
//    @Override
//    public boolean exists(String key) {
//        return jedis.exists(key);
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
//        return jedis.get(key);
//    }
//
//    /**
//     * 新增数据
//     *
//     * @param key   键值
//     * @param value
//     */
//    @Override
//    public String set(String key, Object value) {
//        return jedis.set(key, JSONObject.toJSONString(value));
//    }
//
//    /**
//     * 获取热点数据
//     *
//     * @param key 键值
//     * @return Object
//     */
//    @Override
//    public Object getHotData(String key) {
//        return null;
//    }
//
//    /**
//     * 新增热点数据
//     *
//     * @param key   键值
//     * @param value
//     */
//    @Override
//    public void putHotData(String key, Object value) {
//
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
//
//    }
//
//}
