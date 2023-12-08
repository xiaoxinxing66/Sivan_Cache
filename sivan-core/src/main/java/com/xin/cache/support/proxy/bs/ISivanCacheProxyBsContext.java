package com.xin.cache.support.proxy.bs;


import com.xin.cache.annotation.SivanCacheInterceptor;
import com.xin.cache.api.ISivanCache;

import java.lang.reflect.Method;

/**
 * @author sivan
 *    
 */
public interface ISivanCacheProxyBsContext {

    /**
     * 拦截器信息
     * @return 拦截器
     *    
     */
    SivanCacheInterceptor interceptor();

    /**
     * 获取代理对象信息
     * @return 代理
     *    
     */
    ISivanCache target();

    /**
     * 目标对象
     * @param target 对象
     * @return 结果
     *    
     */
    ISivanCacheProxyBsContext target(final ISivanCache target);

    /**
     * 参数信息
     * @return 参数信息
     *    
     */
    Object[] params();

    /**
     * 方法信息
     * @return 方法信息
     *    
     */
    Method method();

    /**
     * 方法执行
     * @return 执行
     *    
     * @throws Throwable 异常信息
     */
    Object process() throws Throwable;

}
