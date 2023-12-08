package com.xin.cache.api;

import java.util.concurrent.TimeUnit;

/**
 * 持久化缓存接口
 * @author sivan
 * @param <K> key
 * @param <V> value
 */
public interface ISivanCachePersist<K, V> {

    /**
     * 持久化缓存信息
     * @param cache 缓存
     */
    void persist(final ISivanCache<K, V> cache);

    /**
     * 延迟时间
     * @return 延迟
     */
    long delay();

    /**
     * 时间间隔
     * @return 间隔
     */
    long period();

    /**
     * 时间单位
     * @return 时间单位
     */
    TimeUnit timeUnit();
}
