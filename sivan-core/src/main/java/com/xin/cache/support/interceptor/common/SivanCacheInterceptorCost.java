package com.xin.cache.support.interceptor.common;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.xin.cache.api.ISivanCacheInterceptor;
import com.xin.cache.api.ISivanCacheInterceptorContext;
import com.xin.cache.api.ISivanCacheSlowListener;
import com.xin.cache.support.listener.slow.SivanCacheSlowListenerContext;

import java.util.List;

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
public class SivanCacheInterceptorCost<K,V> implements ISivanCacheInterceptor<K,V> {

    private static final Log log = LogFactory.getLog(SivanCacheInterceptorCost.class);

    @Override
    public void before(ISivanCacheInterceptorContext<K,V> context) {
        log.debug("Cost start, method: {}", context.method().getName());
    }

    @Override
    public void after(ISivanCacheInterceptorContext<K,V> context) {
        long costMills = context.endMills()-context.startMills();
        final String methodName = context.method().getName();
        log.debug("Cost end, method: {}, cost: {}ms", methodName, costMills);

        // 添加慢日志操作
        List<ISivanCacheSlowListener> slowListeners = context.cache().slowListeners();
        if(CollectionUtil.isNotEmpty(slowListeners)) {
            SivanCacheSlowListenerContext listenerContext = SivanCacheSlowListenerContext.newInstance().startTimeMills(context.startMills())
                    .endTimeMills(context.endMills())
                    .costTimeMills(costMills)
                    .methodName(methodName)
                    .params(context.params())
                    .result(context.result())
                    ;

            // 设置多个，可以考虑不同的慢日志级别，做不同的处理
            for(ISivanCacheSlowListener slowListener : slowListeners) {
                long slowThanMills = slowListener.slowerThanMills();
                if(costMills >= slowThanMills) {
                    slowListener.listen(listenerContext);
                }
            }
        }
    }

}
