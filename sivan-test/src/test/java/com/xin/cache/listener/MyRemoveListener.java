package com.xin.cache.listener;


import com.xin.cache.api.ISivanCacheRemoveListener;
import com.xin.cache.api.ISivanCacheRemoveListenerContext;

/**
 * @author sivan
 *    
 */
public class MyRemoveListener<K,V> implements ISivanCacheRemoveListener<K,V> {

    @Override
    public void listen(ISivanCacheRemoveListenerContext<K, V> context) {
        System.out.println("【删除提示】可恶，我竟然被删除了！" + context.key());
    }

}
