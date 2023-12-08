package com.xin.cache.support.struct.lru;


import com.xin.cache.api.ISivanCacheEntry;

/**
 * LRU map 接口
 * @author sivan
 *   
 */
public interface ILruMap<K,V> {

    /**
     * 移除最老的元素
     * @return 移除的明细
     *   
     */
    ISivanCacheEntry<K, V> removeEldest();

    /**
     * 更新 key 的信息
     * @param key key
     *   
     */
    void updateKey(final K key);

    /**
     * 移除对应的 key 信息
     * @param key key
     *   
     */
    void removeKey(final K key);

    /**
     * 是否为空
     * @return 是否
     *   
     */
    boolean isEmpty();

    /**
     * 是否包含元素
     * @param key 元素
     * @return 结果
     *   
     */
    boolean contains(final K key);

}
