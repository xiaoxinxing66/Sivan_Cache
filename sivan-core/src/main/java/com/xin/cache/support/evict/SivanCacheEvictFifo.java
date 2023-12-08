package com.xin.cache.support.evict;

import com.xin.cache.api.ISivanCache;
import com.xin.cache.api.ISivanCacheEvictContext;
import com.xin.cache.model.SivanCacheEntry;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 丢弃策略-先进先出
 * @author sivan
 *  
 */
public class SivanCacheEvictFifo<K,V> extends AbstractSivanCacheEvict<K,V> {

    /**
     * queue 信息
     *  
     */
    private final Queue<K> queue = new LinkedList<>();

    @Override
    public SivanCacheEntry<K,V> doEvict(ISivanCacheEvictContext<K, V> context) {
        SivanCacheEntry<K,V> result = null;

        final ISivanCache<K,V> cache = context.cache();
        // 超过限制，执行移除
        if(cache.size() >= context.size()) {
            K evictKey = queue.remove();
            // 移除最开始的元素
            V evictValue = cache.remove(evictKey);
            result = new SivanCacheEntry<>(evictKey, evictValue);
        }

        // 将新加的元素放入队尾
        final K key = context.key();
        queue.add(key);

        return result;
    }

}
