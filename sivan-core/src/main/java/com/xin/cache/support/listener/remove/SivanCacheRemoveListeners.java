package com.xin.cache.support.listener.remove;


import com.xin.cache.api.ISivanCacheRemoveListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 缓存删除监听类
 * @author sivan
 *    
 */
public class SivanCacheRemoveListeners {

    private SivanCacheRemoveListeners(){}

    /**
     * 默认监听类
     * @return 监听类列表
     * @param <K> key
     * @param <V> value
     *    
     */
    @SuppressWarnings("all")
    public static <K,V> List<ISivanCacheRemoveListener<K,V>> defaults() {
        List<ISivanCacheRemoveListener<K,V>> listeners = new ArrayList<>();
        listeners.add(new SivanCacheRemoveListener());
        return listeners;
    }

}
