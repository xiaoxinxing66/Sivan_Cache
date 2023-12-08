package com.xin.cache.api;

import java.util.Map;

/**
 * 缓存上下文
 */
public interface ISivanCacheContext<K, V> {

    /**
     * map 信息
     * @return map
     */
    Map<K, V> map();

    /**
     * 大小限制
     * @return 大小限制
     */
    int size();

    /**
     * 驱除策略
     * @return 策略
     */
    ISivanCacheEvict<K,V> cacheEvict();

}
