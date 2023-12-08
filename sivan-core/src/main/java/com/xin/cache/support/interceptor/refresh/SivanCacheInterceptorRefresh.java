package com.xin.cache.support.interceptor.refresh;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.xin.cache.api.ISivanCache;
import com.xin.cache.api.ISivanCacheInterceptor;
import com.xin.cache.api.ISivanCacheInterceptorContext;

/**
 * 刷新
 *
 * @author sivan
 *    
 */
public class SivanCacheInterceptorRefresh<K,V> implements ISivanCacheInterceptor<K, V> {

    private static final Log log = LogFactory.getLog(SivanCacheInterceptorRefresh.class);

    @Override
    public void before(ISivanCacheInterceptorContext<K,V> context) {
        log.debug("Refresh start");
        final ISivanCache<K,V> cache = context.cache();
        cache.expire().refreshExpire(cache.keySet());
    }

    @Override
    public void after(ISivanCacheInterceptorContext<K,V> context) {
    }

}
