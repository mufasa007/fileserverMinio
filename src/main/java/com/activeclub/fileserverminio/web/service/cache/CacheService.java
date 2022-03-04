package com.activeclub.fileserverminio.web.service.cache;

public interface CacheService {

    /**
     * 判断是否存在
     *
     * @param key 键值
     * @return boolean
     */
    boolean exists(String key);

    /**
     * 获取数据
     *
     * @param key 键值
     * @return Object
     */
    Object get(String key);

    /**
     * 新增数据
     *
     * @param key 键值
     */
    void set(String key, Object value);

    /**
     * 获取热点数据
     *
     * @param key 键值
     * @return Object
     */
    Object getHotData(String key);

    /**
     * 新增热点数据
     *
     * @param key 键值
     */
    void putHotData(String key, Object value);

    /**
     * 新增会自动过期的数据
     *
     * @param key 键值
     */
    void set(String key, Object value, long expirateTime);

    /**
     * 删除数据
     *
     * @param key 键值
     */
    void del(String key);

}
