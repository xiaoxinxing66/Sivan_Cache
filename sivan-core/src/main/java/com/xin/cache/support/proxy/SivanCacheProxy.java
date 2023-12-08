/*
 * Copyright (c)  2019. houbinbin Inc.
 * async All rights reserved.
 */

package com.xin.cache.support.proxy;


import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.xin.cache.api.ISivanCache;
import com.xin.cache.support.proxy.cglib.CglibProxy;
import com.xin.cache.support.proxy.dynamic.DynamicProxy;
import com.xin.cache.support.proxy.none.NoneProxy;

import java.lang.reflect.Proxy;

/**
 * <p> 代理信息 </p>
 *
 * <pre> Created: 2019/3/8 10:38 AM  </pre>
 * <pre> Project: async  </pre>
 *
 * @author houbinbin
 *    
 */
public final class SivanCacheProxy {

    private SivanCacheProxy(){}

    /**
     * 获取对象代理
     * @param <K> 泛型 key
     * @param <V> 泛型 value
     * @param cache 对象代理
     * @return 代理信息
     *    
     */
    @SuppressWarnings("all")
    public static <K,V> ISivanCache<K,V> getProxy(final ISivanCache<K,V> cache) {
        if(ObjectUtil.isNull(cache)) {
            return (ISivanCache<K,V>) new NoneProxy(cache).proxy();
        }

        final Class clazz = cache.getClass();

        // 如果targetClass本身是个接口或者targetClass是JDK Proxy生成的,则使用JDK动态代理。
        // 参考 spring 的 AOP 判断
        if (clazz.isInterface() || Proxy.isProxyClass(clazz)) {
            return (ISivanCache<K,V>) new DynamicProxy(cache).proxy();
        }

        return (ISivanCache<K,V>) new CglibProxy(cache).proxy();
    }

}
