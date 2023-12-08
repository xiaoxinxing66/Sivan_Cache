package com.xin.cache.support.load;


import com.xin.cache.api.ISivanCache;
import com.xin.cache.api.ISivanCacheLoad;

/**
 * 加载策略-无
 * @author sivan
 *    
 */
public class SivanCacheLoadNone<K,V> implements ISivanCacheLoad<K,V> {

    @Override
    public void load(ISivanCache<K, V> cache) {
        //nothing...
    }

}
