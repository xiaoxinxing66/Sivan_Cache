package com.xin.cache.support.evict;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.xin.cache.api.ISivanCache;
import com.xin.cache.api.ISivanCacheEntry;
import com.xin.cache.api.ISivanCacheEvict;
import com.xin.cache.api.ISivanCacheEvictContext;
import com.xin.cache.model.SivanCacheEntry;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 丢弃策略-LRU 最近最少使用
 *
 * 实现方式：LinkedHashMap
 * @author sivan
 *   
 */
public class SivanCacheEvictLruLinkedHashMap<K,V> extends LinkedHashMap<K,V>
    implements ISivanCacheEvict<K,V> {

    private static final Log log = LogFactory.getLog(SivanCacheEvictLruDoubleListMap.class);

    /**
     * 是否移除标识
     *   
     */
    private volatile boolean removeFlag = false;

    /**
     * 最旧的一个元素
     *   
     */
    private transient Map.Entry<K, V> eldest = null;

    public SivanCacheEvictLruLinkedHashMap() {
        super(16, 0.75f, true);
    }

    @Override
    public ISivanCacheEntry<K, V> evict(ISivanCacheEvictContext<K, V> context) {
        ISivanCacheEntry<K, V> result = null;
        final ISivanCache<K,V> cache = context.cache();
        // 超过限制，移除队尾的元素
        if(cache.size() >= context.size()) {
            removeFlag = true;

            // 执行 put 操作
            super.put(context.key(), null);

            // 构建淘汰的元素
            K evictKey = eldest.getKey();
            V evictValue = cache.remove(evictKey);
            result = new SivanCacheEntry<>(evictKey, evictValue);
        } else {
            removeFlag = false;
        }

        return result;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        this.eldest = eldest;
        return removeFlag;
    }

    @Override
    public void updateKey(K key) {
        super.put(key, null);
    }

    @Override
    public void removeKey(K key) {
        super.remove(key);
    }

}
