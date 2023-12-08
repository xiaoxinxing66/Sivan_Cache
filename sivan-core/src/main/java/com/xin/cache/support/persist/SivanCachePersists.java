package com.xin.cache.support.persist;


import com.xin.cache.api.ISivanCachePersist;

/**
 * 缓存持久化工具类
 * @author sivan
 *    
 */
public final class SivanCachePersists {

    private SivanCachePersists(){}

    /**
     * 无操作
     * @param <K> key
     * @param <V> value
     * @return 结果
     *    
     */
    public static <K,V> ISivanCachePersist<K,V> none() {
        return new SivanCachePersistNone<>();
    }

    /**
     * DB json 操作
     * @param <K> key
     * @param <V> value
     * @param path 文件路径
     * @return 结果
     *    
     */
    public static <K,V> ISivanCachePersist<K,V> dbJson(final String path) {
        return new SivanCachePersistDbJson<>(path);
    }

    /**
     * AOF 持久化
     * @param <K> key
     * @param <V> value
     * @param path 文件路径
     * @return 结果
     *   
     */
    public static <K,V> ISivanCachePersist<K,V> aof(final String path) {
        return new SivanCachePersistAof<>(path);
    }

}
