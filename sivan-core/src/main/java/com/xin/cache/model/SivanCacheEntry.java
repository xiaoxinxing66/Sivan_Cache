package com.xin.cache.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.xin.cache.api.ISivanCacheEntry;
import com.xin.cache.api.ISivanCacheEvictContext;

public class SivanCacheEntry<K,V> implements ISivanCacheEntry<K,V> {

/**
     * key
     *  1
     */
    @JSONField(ordinal = 1)
    private final K key;

    /**
     * value
     *  1
     */
    @JSONField(ordinal = 2)
    private final V value;

    /**
     * 新建元素
     * @param key key
     * @param value value
     * @param <K> 泛型
     * @param <V> 泛型
     * @return 结果
     *  1
     */
    public static <K,V> SivanCacheEntry<K,V> of(final K key,
                                           final V value) {
        return new SivanCacheEntry<>(key, value);
    }

    public SivanCacheEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K key() {
        return key;
    }

    @Override
    public V value() {
        return value;
    }

    @Override
    public String toString() {
        return "EvictEntry{" +
                "key=" + key +
                ", value=" + value +
                '}';

    }

}
