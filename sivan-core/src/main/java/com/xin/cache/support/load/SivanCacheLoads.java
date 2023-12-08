package com.xin.cache.support.load;


import com.xin.cache.api.ISivanCacheLoad;

/**
 *
 * 加载策略工具类
 * @author sivan
 *    
 */
public final class SivanCacheLoads {

    private SivanCacheLoads(){}

    /**
     * 无加载
     * @param <K> key
     * @param <V> value
     * @return 值
     *    
     */
    public static <K,V> ISivanCacheLoad<K,V> none() {
        return new SivanCacheLoadNone<>();
    }

    /**
     * 文件 JSON
     * @param dbPath 文件路径
     * @param <K> key
     * @param <V> value
     * @return 值
     *    
     */
    public static <K,V> ISivanCacheLoad<K,V> dbJson(final String dbPath) {
        return new SivanCacheLoadDbJson<>(dbPath);
    }

    /**
     * AOF 文件加载模式
     * @param dbPath 文件路径
     * @param <K> key
     * @param <V> value
     * @return 值
     *   
     */
    public static <K,V> ISivanCacheLoad<K,V> aof(final String dbPath) {
        return new SivanCacheLoadAof<>(dbPath);
    }

}
