package com.xin.cache.support.proxy.bs;


import com.xin.cache.annotation.SivanCacheInterceptor;
import com.xin.cache.api.ISivanCache;

import java.lang.reflect.Method;

/**
 * 代理引导类上下文
 * @author sivan
 *    
 */
public class SivanCacheProxyBsContext implements ISivanCacheProxyBsContext {

    /**
     * 目标
     *    
     */
    private ISivanCache target;

    /**
     * 入参
     *    
     */
    private Object[] params;

    /**
     * 方法
     *    
     */
    private Method method;

    /**
     * 拦截器
     *    
     */
    private SivanCacheInterceptor interceptor;

    /**
     * 新建对象
     * @return 对象
     *    
     */
    public static SivanCacheProxyBsContext newInstance(){
        return new SivanCacheProxyBsContext();
    }

    @Override
    public ISivanCache target() {
        return target;
    }

    @Override
    public SivanCacheProxyBsContext target(ISivanCache target) {
        this.target = target;
        return this;
    }

    @Override
    public Object[] params() {
        return params;
    }

    public SivanCacheProxyBsContext params(Object[] params) {
        this.params = params;
        return this;
    }

    @Override
    public Method method() {
        return method;
    }

    @Override
    public Object process() throws Throwable {
        return this.method.invoke(target, params);
    }

    public SivanCacheProxyBsContext method(Method method) {
        this.method = method;
        this.interceptor = method.getAnnotation(SivanCacheInterceptor.class);
        return this;
    }

    @Override
    public SivanCacheInterceptor interceptor() {
        return interceptor;
    }
}
