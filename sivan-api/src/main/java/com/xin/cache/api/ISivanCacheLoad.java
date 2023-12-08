package com.xin.cache.api;

/**
 * 缓存接口
 * @author sivan
 * @param <K> key
 * @param <V> value
 */
public interface ISivanCacheLoad<K, V> {

    /**
     * 加载缓存信息
     * @param cache 缓存
     */
    void load(final ISivanCache<K,V> cache);

}
