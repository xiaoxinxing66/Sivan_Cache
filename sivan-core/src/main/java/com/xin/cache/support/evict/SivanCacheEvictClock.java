package com.xin.cache.support.evict;


import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.xin.cache.api.ISivanCache;
import com.xin.cache.api.ISivanCacheEntry;
import com.xin.cache.api.ISivanCacheEvictContext;
import com.xin.cache.model.SivanCacheEntry;
import com.xin.cache.support.struct.lru.ILruMap;
import com.xin.cache.support.struct.lru.impl.LruMapCircleList;

/**
 * 淘汰策略-clock 算法
 *
 * @author sivan
 */
public class SivanCacheEvictClock<K,V> extends AbstractSivanCacheEvict<K,V> {

    private static final Log log = LogFactory.getLog(SivanCacheEvictClock.class);

    /**
     * 循环链表
     *   
     */
    private final ILruMap<K,V> circleList;

    public SivanCacheEvictClock() {
        this.circleList = new LruMapCircleList<>();
    }

    @Override
    protected ISivanCacheEntry<K, V> doEvict(ISivanCacheEvictContext<K, V> context) {
        ISivanCacheEntry<K, V> result = null;
        final ISivanCache<K,V> cache = context.cache();
        // 超过限制，移除队尾的元素
        if(cache.size() >= context.size()) {
            ISivanCacheEntry<K,V>  evictEntry = circleList.removeEldest();;
            // 执行缓存移除操作
            final K evictKey = evictEntry.key();
            V evictValue = cache.remove(evictKey);

            log.debug("基于 clock 算法淘汰 key：{}, value: {}", evictKey, evictValue);
            result = new SivanCacheEntry<>(evictKey, evictValue);
        }

        return result;
    }


    /**
     * 更新信息
     * @param key 元素
     */
    @Override
    public void updateKey(final K key) {
        this.circleList.updateKey(key);
    }

    /**
     * 移除元素
     *
     * @param key 元素
     */
    @Override
    public void removeKey(final K key) {
        this.circleList.removeKey(key);
    }

}
