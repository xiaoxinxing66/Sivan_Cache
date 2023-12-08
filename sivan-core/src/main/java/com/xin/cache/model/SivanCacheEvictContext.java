package com.xin.cache.model;

import com.xin.cache.api.ISivanCache;
import com.xin.cache.api.ISivanCacheEvictContext;

/**
 * @Author 不知名网友鑫
 * @Date 2023/11/6
 **/
public class SivanCacheEvictContext<K,V> implements ISivanCacheEvictContext<K,V> {

    private int size;
    private K key;
    private V value;
    private ISivanCache<K,V> cache;

    public SivanCacheEvictContext<K,V> key(K key) {
        this.key = key;
        return this;
    }
    public SivanCacheEvictContext<K, V> size(int size) {
        this.size = size;
        return this;
    }
    public SivanCacheEvictContext<K,V> cache(ISivanCache<K, V> cache) {
        this.cache = cache;
        return this;
    }

    @Override
    public K key() {
        return key;
    }

    @Override
    public ISivanCache<K, V> cache() {
        return cache;
    }

    @Override
    public int size() {
        return size;
    }
}
