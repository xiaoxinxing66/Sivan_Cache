package com.xin.cache.api;

/**
 * 删除监听器接口
 *
 * @author sivan
 * @param <K> key
 * @param <V> value
 */
public interface ISivanCacheRemoveListener<K,V> {

    /**
     * 监听
     * @param context 上下文
     */
    void listen(final ISivanCacheRemoveListenerContext<K,V> context);

}
