package com.xin.cache.support.evict;

import com.xin.cache.api.ISivanCacheEntry;
import com.xin.cache.api.ISivanCacheEvictContext;

/**
 * 丢弃策略
 * @author sivan
 *  
 */
public class SivanCacheEvictNone<K,V> extends AbstractSivanCacheEvict<K,V> {

    @Override
    protected ISivanCacheEntry<K, V> doEvict(ISivanCacheEvictContext<K, V> context) {
        return null;
    }

}
