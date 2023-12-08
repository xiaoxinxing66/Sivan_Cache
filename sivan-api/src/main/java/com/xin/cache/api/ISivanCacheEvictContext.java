package com.xin.cache.api;

/**
 * 驱逐策略上下文
 * @param <K>
 * @param <V>
 */
public interface ISivanCacheEvictContext<K,V> {

    /**
     * 新加的 key
     * @return key
     */
    K key();

    /**
     * cache 实现
     * @return map
     */
    ISivanCache<K, V> cache();

    /**
     * 获取大小
     * @return 大小
     */
    int size();

}