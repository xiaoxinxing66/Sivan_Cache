package com.xin.cache.support.evict;


import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.xin.cache.api.ISivanCache;
import com.xin.cache.api.ISivanCacheEntry;
import com.xin.cache.api.ISivanCacheEvictContext;
import com.xin.cache.model.SivanCacheEntry;

import java.util.LinkedList;
import java.util.List;

/**
 * 丢弃策略-LRU 最近最少使用
 * @author sivan
 *   
 */
public class SivanCacheEvictLru<K,V> extends AbstractSivanCacheEvict<K,V> {

    private static final Log log = LogFactory.getLog(SivanCacheEvictLru.class);

    /**
     * list 信息
     *   
     */
    private final List<K> list = new LinkedList<>();

    @Override
    protected ISivanCacheEntry<K, V> doEvict(ISivanCacheEvictContext<K, V> context) {
        ISivanCacheEntry<K, V> result = null;
        final ISivanCache<K,V> cache = context.cache();
        // 超过限制，移除队尾的元素
        if(cache.size() >= context.size()) {
            K evictKey = list.get(list.size()-1);
            V evictValue = cache.remove(evictKey);
            result = new SivanCacheEntry<>(evictKey, evictValue);
        }

        return result;
    }


    /**
     * 放入元素
     * （1）删除已经存在的
     * （2）新元素放到元素头部
     *
     * @param key 元素
     *   
     */
    @Override
    public void updateKey(final K key) {
        this.list.remove(key);
        this.list.add(0, key);
    }

    /**
     * 移除元素
     * @param key 元素
     *   
     */
    @Override
    public void removeKey(final K key) {
        this.list.remove(key);
    }

}
