package com.xin.cache.support.listener.remove;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.xin.cache.api.ISivanCacheRemoveListener;
import com.xin.cache.api.ISivanCacheRemoveListenerContext;

/**
 * 默认的删除监听类
 * @author sivan
 *    
 */
public class SivanCacheRemoveListener<K,V> implements ISivanCacheRemoveListener<K,V> {

    private static final Log log = LogFactory.getLog(SivanCacheRemoveListener.class);

    @Override
    public void listen(ISivanCacheRemoveListenerContext<K, V> context) {
        log.debug("Remove key: {}, value: {}, type: {}",
                context.key(), context.value(), context.type());
    }

}
