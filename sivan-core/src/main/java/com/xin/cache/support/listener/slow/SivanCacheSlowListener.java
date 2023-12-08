package com.xin.cache.support.listener.slow;

import com.alibaba.fastjson.JSON;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.xin.cache.api.ISivanCacheSlowListener;
import com.xin.cache.api.ISivanCacheSlowListenerContext;
import com.xin.cache.support.interceptor.common.SivanCacheInterceptorCost;

/**
 * 慢日志监听类
 * @author sivan
 *    
 */
public class SivanCacheSlowListener implements ISivanCacheSlowListener {

    private static final Log log = LogFactory.getLog(SivanCacheInterceptorCost.class);

    @Override
    public void listen(ISivanCacheSlowListenerContext context) {
        log.warn("[Slow] methodName: {}, params: {}, cost time: {}",
                context.methodName(), JSON.toJSON(context.params()), context.costTimeMills());
    }

    @Override
    public long slowerThanMills() {
        return 1000L;
    }

}
