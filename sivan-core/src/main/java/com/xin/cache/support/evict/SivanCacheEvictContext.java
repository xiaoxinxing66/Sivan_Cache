package com.xin.cache.support.evict;


import com.xin.cache.api.ISivanCache;
import com.xin.cache.api.ISivanCacheEvictContext;

/**
 * 驱除策略
 *
 * 1. 新加的 key
 * 2. map 实现
 * 3. 淘汰监听器
 *
 * @author sivan
 *  
 * @param <K> key
 * @param <V> value
 */
public class SivanCacheEvictContext<K,V> implements ISivanCacheEvictContext<K,V> {

    /**
     * 新加的 key
     *  
     */
    private K key;

    /**
     * cache 实现
     *  
     */
    private ISivanCache<K,V> cache;

    /**
     * 最大的大小
     *  
     */
    private int size;

    @Override
    public K key() {
        return key;
    }

    public SivanCacheEvictContext<K, V> key(K key) {
        this.key = key;
        return this;
    }

    @Override
    public ISivanCache<K, V> cache() {
        return cache;
    }

    public SivanCacheEvictContext<K, V> cache(ISivanCache<K, V> cache) {
        this.cache = cache;
        return this;
    }

    @Override
    public int size() {
        return size;
    }

    public SivanCacheEvictContext<K, V> size(int size) {
        this.size = size;
        return this;
    }
}
