package com.xin.cache.api;

/**
 * 拦截器接口
 *
 * （1）耗时统计
 * （2）监听器
 *
 * @author sivan
 * @param <K> key
 * @param <V> value
 */
public interface ISivanCacheInterceptor<K,V> {

    /**
     * 方法执行之前
     * @param context 上下文
     */
    void before(ISivanCacheInterceptorContext<K,V> context);

    /**
     * 方法执行之后
     * @param context 上下文
     */
    void after(ISivanCacheInterceptorContext<K,V> context);

}
