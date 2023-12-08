package com.xin.cache.api;

/**
 * 淘汰策略接口
 */
public interface ISivanCacheEvict<K,V> {
    /**
     * 淘汰策略
     * @param context
     * @return
     */
    ISivanCacheEntry<K,V> evict(ISivanCacheEvictContext<K,V> context);

    /**
     * 更新key信息
     * @param key
     */
    void updateKey(K key);

    /**
     * 删除key信息
     * @param key
     */
    void removeKey(K key);
}
