package com.xin.cache.support.interceptor.evict;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.xin.cache.api.ISivanCacheEvict;
import com.xin.cache.api.ISivanCacheInterceptor;
import com.xin.cache.api.ISivanCacheInterceptorContext;

import java.lang.reflect.Method;

/**
 * 驱除策略拦截器
 *
 * @author sivan
 *   
 */
public class SivanCacheInterceptorEvict<K,V> implements ISivanCacheInterceptor<K, V> {

    private static final Log log = LogFactory.getLog(SivanCacheInterceptorEvict.class);

    @Override
    public void before(ISivanCacheInterceptorContext<K,V> context) {
    }

    @Override
    @SuppressWarnings("all")
    public void after(ISivanCacheInterceptorContext<K,V> context) {
        ISivanCacheEvict<K,V> evict = context.cache().evict();

        Method method = context.method();
        final K key = (K) context.params()[0];
        if("remove".equals(method.getName())) {
            evict.removeKey(key);
        } else {
            evict.updateKey(key);
        }
    }

}
