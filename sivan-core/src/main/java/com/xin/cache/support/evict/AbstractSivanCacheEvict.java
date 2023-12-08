package com.xin.cache.support.evict;


import com.xin.cache.api.ISivanCacheEntry;
import com.xin.cache.api.ISivanCacheEvict;
import com.xin.cache.api.ISivanCacheEvictContext;

/**
 * 丢弃策略-抽象实现类
 * @author sivan
 */
public abstract class AbstractSivanCacheEvict<K,V> implements ISivanCacheEvict<K,V> {

    @Override
    public ISivanCacheEntry<K,V> evict(ISivanCacheEvictContext<K, V> context) {
        //3. 返回结果
        return doEvict(context);
    }

    /**
     * 执行移除
     * @param context 上下文
     * @return 结果
     */
    protected abstract ISivanCacheEntry<K,V> doEvict(ISivanCacheEvictContext<K, V> context);

    @Override
    public void updateKey(K key) {

    }

    @Override
    public void removeKey(K key) {

    }
}
