package com.xin.cache.bs;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.xin.cache.api.*;
import com.xin.cache.core.SivanCache;
import com.xin.cache.support.evict.SivanCacheEvicts;
import com.xin.cache.support.listener.remove.SivanCacheRemoveListeners;
import com.xin.cache.support.listener.slow.SivanCacheSlowListeners;
import com.xin.cache.support.load.SivanCacheLoads;
import com.xin.cache.support.persist.SivanCachePersists;
import com.xin.cache.support.proxy.SivanCacheProxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 缓存引导类
 * @author sivan
 *  
 */
public final class SivanCacheBs<K,V> {

    private SivanCacheBs(){}

    /**
     * 创建对象实例
     * @param <K> key
     * @param <V> value
     * @return this
     *  
     */
    public static <K,V> SivanCacheBs<K,V> newInstance() {
        return new SivanCacheBs<>();
    }

    /**
     * map 实现
     *  
     */
    private Map<K,V> map = new HashMap<>();

    /**
     * 大小限制
     *  
     */
    private int size = Integer.MAX_VALUE;

    /**
     * 驱除策略
     *  
     */
    private ISivanCacheEvict<K,V> evict = SivanCacheEvicts.fifo();

    /**
     * 删除监听类
     *  
     */
    private final List<ISivanCacheRemoveListener<K,V>> removeListeners = SivanCacheRemoveListeners.defaults();

    /**
     * 慢操作监听类
     *  
     */
    private final List<ISivanCacheSlowListener> slowListeners = SivanCacheSlowListeners.none();

    /**
     * 加载策略
     *  
     */
    private ISivanCacheLoad<K,V> load = SivanCacheLoads.none();

    /**
     * 持久化实现策略
     *  
     */
    private ISivanCachePersist<K,V> persist = SivanCachePersists.none();

    /**
     * map 实现
     * @param map map
     * @return this
     *  
     */
    public SivanCacheBs<K, V> map(Map<K, V> map) {
        ArgUtil.notNull(map, "map");

        this.map = map;
        return this;
    }

    /**
     * 设置 size 信息
     * @param size size
     * @return this
     *  
     */
    public SivanCacheBs<K, V> size(int size) {
        ArgUtil.notNegative(size, "size");

        this.size = size;
        return this;
    }

    /**
     * 设置驱除策略
     * @param evict 驱除策略
     * @return this
     *  
     */
    public SivanCacheBs<K, V> evict(ISivanCacheEvict<K, V> evict) {
        ArgUtil.notNull(evict, "evict");

        this.evict = evict;
        return this;
    }

    /**
     * 设置加载
     * @param load 加载
     * @return this
     *  
     */
    public SivanCacheBs<K, V> load(ISivanCacheLoad<K, V> load) {
        ArgUtil.notNull(load, "load");

        this.load = load;
        return this;
    }

    /**
     * 添加删除监听器
     * @param removeListener 监听器
     * @return this
     *  
     */
    public SivanCacheBs<K, V> addRemoveListener(ISivanCacheRemoveListener<K,V> removeListener) {
        ArgUtil.notNull(removeListener, "removeListener");

        this.removeListeners.add(removeListener);
        return this;
    }

    /**
     * 添加慢日志监听器
     * @param slowListener 监听器
     * @return this
     *  
     */
    public SivanCacheBs<K, V> addSlowListener(ISivanCacheSlowListener slowListener) {
        ArgUtil.notNull(slowListener, "slowListener");

        this.slowListeners.add(slowListener);
        return this;
    }

    /**
     * 设置持久化策略
     * @param persist 持久化
     * @return this
     *  
     */
    public SivanCacheBs<K, V> persist(ISivanCachePersist<K, V> persist) {
        this.persist = persist;
        return this;
    }

    /**
     * 构建缓存信息
     * @return 缓存信息
     *  
     */
    public ISivanCache<K,V> build() {
        SivanCache<K,V> cache = new SivanCache<>();
        cache.map(map);
        cache.evict(evict);
        cache.sizeLimit(size);
        cache.removeListeners(removeListeners);
        cache.load(load);
        cache.persist(persist);
        cache.slowListeners(slowListeners);

        // 初始化
        cache.init();
        return SivanCacheProxy.getProxy(cache);
    }

}
