package com.xin.cache.support.interceptor.aof;

import com.alibaba.fastjson.JSON;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.xin.cache.api.ISivanCache;
import com.xin.cache.api.ISivanCacheInterceptor;
import com.xin.cache.api.ISivanCacheInterceptorContext;
import com.xin.cache.api.ISivanCachePersist;
import com.xin.cache.model.PersistAofEntry;
import com.xin.cache.support.persist.SivanCachePersistAof;

/**
 * 顺序追加模式
 *
 * AOF 持久化到文件，暂时不考虑 buffer 等特性。
 * @author sivan
 *   
 */
public class SivanCacheInterceptorAof<K,V> implements ISivanCacheInterceptor<K, V> {

    private static final Log log = LogFactory.getLog(SivanCacheInterceptorAof.class);

    @Override
    public void before(ISivanCacheInterceptorContext<K,V> context) {
    }

    @Override
    public void after(ISivanCacheInterceptorContext<K,V> context) {
        // 持久化类
        ISivanCache<K,V> cache = context.cache();
        ISivanCachePersist<K,V> persist = cache.persist();

        if(persist instanceof SivanCachePersistAof) {
            SivanCachePersistAof<K,V> cachePersistAof = (SivanCachePersistAof<K,V>) persist;

            String methodName = context.method().getName();
            PersistAofEntry aofEntry = PersistAofEntry.newInstance();
            aofEntry.setMethodName(methodName);
            aofEntry.setParams(context.params());

            String json = JSON.toJSONString(aofEntry);

            // 直接持久化
            log.debug("AOF 开始追加文件内容：{}", json);
            cachePersistAof.append(json);
            log.debug("AOF 完成追加文件内容：{}", json);
        }
    }

}
