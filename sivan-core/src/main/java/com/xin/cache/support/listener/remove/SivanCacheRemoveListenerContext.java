package com.xin.cache.support.listener.remove;


import com.xin.cache.api.ISivanCacheRemoveListenerContext;

/**
 * 删除的监听器
 * @author sivan
 *    
 */
public class SivanCacheRemoveListenerContext<K,V> implements ISivanCacheRemoveListenerContext<K,V> {

    /**
     * key
     *    
     */
    private K key;

    /**
     * 值
     *    
     */
    private V value;

    /**
     * 删除类型
     *    
     */
    private String type;

    /**
     * 新建实例
     * @param <K> key
     * @param <V> value
     * @return 结果
     *    
     */
    public static <K,V> SivanCacheRemoveListenerContext<K,V> newInstance() {
        return new SivanCacheRemoveListenerContext<>();
    }

    @Override
    public K key() {
        return key;
    }

    public SivanCacheRemoveListenerContext<K, V> key(K key) {
        this.key = key;
        return this;
    }

    @Override
    public V value() {
        return value;
    }

    public SivanCacheRemoveListenerContext<K, V> value(V value) {
        this.value = value;
        return this;
    }

    @Override
    public String type() {
        return type;
    }

    public SivanCacheRemoveListenerContext<K, V> type(String type) {
        this.type = type;
        return this;
    }
}
