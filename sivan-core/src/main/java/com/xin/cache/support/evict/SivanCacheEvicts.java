package com.xin.cache.support.evict;


import com.xin.cache.api.ISivanCacheEvict;

/**
 * 丢弃策略
 *
 * @author sivan
 *  
 */
public final class SivanCacheEvicts {

    private SivanCacheEvicts(){}

    /**
     * 无策略
     *
     * @param <K> key
     * @param <V> value
     * @return 结果
     *  
     */
    public static <K, V> ISivanCacheEvict<K, V> none() {
        return new SivanCacheEvictNone<>();
    }

    /**
     * 先进先出
     *
     * @param <K> key
     * @param <V> value
     * @return 结果
     *  
     */
    public static <K, V> ISivanCacheEvict<K, V> fifo() {
        return new SivanCacheEvictFifo<>();
    }

    /**
     * LRU 驱除策略
     *
     * @param <K> key
     * @param <V> value
     * @return 结果
     *   
     */
    public static <K, V> ISivanCacheEvict<K, V> lru() {
        return new SivanCacheEvictLru<>();
    }

    /**
     * LRU 驱除策略
     *
     * 基于双向链表 + map 实现
     * @param <K> key
     * @param <V> value
     * @return 结果
     *   
     */
    public static <K, V> ISivanCacheEvict<K, V> lruDoubleListMap() {
        return new SivanCacheEvictLruDoubleListMap<>();
    }


    /**
     * LRU 驱除策略
     *
     * 基于LinkedHashMap
     * @param <K> key
     * @param <V> value
     * @return 结果
     *   
     */
    public static <K, V> ISivanCacheEvict<K, V> lruLinkedHashMap() {
        return new SivanCacheEvictLruLinkedHashMap<>();
    }

    /**
     * LRU 驱除策略
     *
     * 基于 2Q 实现
     * @param <K> key
     * @param <V> value
     * @return 结果
     *   
     */
    public static <K, V> ISivanCacheEvict<K, V> lru2Q() {
        return new SivanCacheEvictLru2Q<>();
    }

    /**
     * LRU 驱除策略
     *
     * 基于 LRU-2 实现
     * @param <K> key
     * @param <V> value
     * @return 结果
     *   
     */
    public static <K, V> ISivanCacheEvict<K, V> lru2() {
        return new SivanCacheEvictLru2<>();
    }

    /**
     * LFU 驱除策略
     *
     * 基于 LFU 实现
     * @param <K> key
     * @param <V> value
     * @return 结果
     */
    public static <K, V> ISivanCacheEvict<K, V> lfu() {
        return new SivanCacheEvictLfu<>();
    }

    /**
     * 时钟算法
     * @param <K> key
     * @param <V> value
     * @return 结果
     */
    public static <K, V> ISivanCacheEvict<K, V> clock() {
        return new SivanCacheEvictClock<>();
    }

}
