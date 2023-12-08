package com.xin.cache.api;

/**
 * 缓存删除 监听器 上下文
 */
public interface ISivanCacheRemoveListenerContext<K,V> {

    /**
     * 要删除的key
     * @return
     */
    K key();

    /**
     * 要删除的value
     * @return
     */
    V value();

    /**
     * 删除类型
     * @return
     */
    String type();
}
