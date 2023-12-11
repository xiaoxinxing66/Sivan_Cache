package com.xin.cache.listener;

import com.xin.cache.api.ISivanCacheSlowListener;
import com.xin.cache.api.ISivanCacheSlowListenerContext;

/**
 * @author sivan
 *    
 */
public class MySlowListener implements ISivanCacheSlowListener {

    @Override
    public void listen(ISivanCacheSlowListenerContext context) {
        System.out.println("【慢日志】name: " + context.methodName());
    }

    @Override
    public long slowerThanMills() {
        return 0;
    }

}
