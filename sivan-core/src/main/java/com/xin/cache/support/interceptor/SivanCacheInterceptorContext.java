package com.xin.cache.support.interceptor;


import com.xin.cache.api.ISivanCache;
import com.xin.cache.api.ISivanCacheInterceptorContext;

import java.lang.reflect.Method;

/**
 * 耗时统计
 *
 * （1）耗时
 * （2）慢日志
 * @author sivan
 *    
 * @param <K> key
 * @param <V> value
 */
public class SivanCacheInterceptorContext<K,V> implements ISivanCacheInterceptorContext<K,V> {

    private ISivanCache<K,V> cache;

    /**
     * 执行的方法信息
     *    
     */
    private Method method;

    /**
     * 执行的参数
     *    
     */
    private Object[] params;

    /**
     * 方法执行的结果
     *    
     */
    private Object result;

    /**
     * 开始时间
     *    
     */
    private long startMills;

    /**
     * 结束时间
     *    
     */
    private long endMills;

    public static <K,V> SivanCacheInterceptorContext<K,V> newInstance() {
        return new SivanCacheInterceptorContext<>();
    }

    @Override
    public ISivanCache<K, V> cache() {
        return cache;
    }

    public SivanCacheInterceptorContext<K, V> cache(ISivanCache<K, V> cache) {
        this.cache = cache;
        return this;
    }

    @Override
    public Method method() {
        return method;
    }

    public SivanCacheInterceptorContext<K, V> method(Method method) {
        this.method = method;
        return this;
    }

    @Override
    public Object[] params() {
        return params;
    }

    public SivanCacheInterceptorContext<K, V> params(Object[] params) {
        this.params = params;
        return this;
    }

    @Override
    public Object result() {
        return result;
    }

    public SivanCacheInterceptorContext<K, V> result(Object result) {
        this.result = result;
        return this;
    }

    @Override
    public long startMills() {
        return startMills;
    }

    public SivanCacheInterceptorContext<K, V> startMills(long startMills) {
        this.startMills = startMills;
        return this;
    }

    @Override
    public long endMills() {
        return endMills;
    }

    public SivanCacheInterceptorContext<K, V> endMills(long endMills) {
        this.endMills = endMills;
        return this;
    }
}
