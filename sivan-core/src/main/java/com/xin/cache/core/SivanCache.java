package com.xin.cache.core;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.xin.cache.annotation.SivanCacheInterceptor;
import com.xin.cache.api.*;
import com.xin.cache.constant.SivanCacheRemoveType;
import com.xin.cache.exception.SivanCacheRuntimeException;
import com.xin.cache.model.SivanCacheEvictContext;
import com.xin.cache.support.expire.SivanCacheExpire;
import com.xin.cache.support.listener.remove.SivanCacheRemoveListenerContext;
import com.xin.cache.support.persist.InnerSivanCachePersist;
import com.xin.cache.support.proxy.SivanCacheProxy;

import java.util.*;

/**
 * 缓存信息
 *
 * @author sivan
 * @param <K> key
 * @param <V> value
 *
 */
public class SivanCache<K,V> implements ISivanCache<K,V> {

    /**
     * map 信息
     *
     */
    private Map<K,V> map;

    /**
     * 大小限制
     *
     */
    private int sizeLimit;

    /**
     * 驱除策略
     *
     */
    private ISivanCacheEvict<K,V> evict;

    /**
     * 过期策略
     * 暂时不做暴露
     *
     */
    private ISivanCacheExpire<K,V> expire;

    /**
     * 删除监听类
     *
     */
    private List<ISivanCacheRemoveListener<K,V>> removeListeners;

    /**
     * 慢日志监听类
     *
     */
    private List<ISivanCacheSlowListener> slowListeners;

    /**
     * 加载类
     *
     */
    private ISivanCacheLoad<K,V> load;

    /**
     * 持久化
     *
     */
    private ISivanCachePersist<K,V> persist;

    /**
     * 设置 map 实现
     * @param map 实现
     * @return this
     */
    public SivanCache<K, V> map(Map<K, V> map) {
        this.map = map;
        return this;
    }

    /**
     * 设置大小限制
     * @param sizeLimit 大小限制
     * @return this
     */
    public SivanCache<K, V> sizeLimit(int sizeLimit) {
        this.sizeLimit = sizeLimit;
        return this;
    }

    /**
     * 设置驱除策略
     * @param cacheEvict 驱除策略
     * @return this
     *
     */
    public SivanCache<K, V> evict(ISivanCacheEvict<K, V> cacheEvict) {
        this.evict = cacheEvict;
        return this;
    }

    /**
     * 获取持久化类
     * @return 持久化类
     *
     */
    @Override
    public ISivanCachePersist<K, V> persist() {
        return persist;
    }


    /**
     * 获取驱除策略
     * @return 驱除策略
     *
     */
    @Override
    public ISivanCacheEvict<K, V> evict() {
        return this.evict;
    }

    /**
     * 设置持久化策略
     * @param persist 持久化
     *
     */
    public void persist(ISivanCachePersist<K, V> persist) {
        this.persist = persist;
    }

    @Override
    public List<ISivanCacheRemoveListener<K, V>> removeListeners() {
        return removeListeners;
    }

    public SivanCache<K, V> removeListeners(List<ISivanCacheRemoveListener<K, V>> removeListeners) {
        this.removeListeners = removeListeners;
        return this;
    }


    @Override
    public List<ISivanCacheSlowListener> slowListeners() {
        return slowListeners;
    }

    public SivanCache<K, V> slowListeners(List<ISivanCacheSlowListener> slowListeners) {
        this.slowListeners = slowListeners;
        return this;
    }

    @Override
    public ISivanCacheLoad<K, V> load() {
        return load;
    }

    public SivanCache<K, V> load(ISivanCacheLoad<K, V> load) {
        this.load = load;
        return this;
    }

    /**
     * 初始化
     *
     */
    public void init() {
        this.expire = new SivanCacheExpire<>(this);
        this.load.load(this);

        // 初始化持久化
        if(this.persist != null) {
            new InnerSivanCachePersist<>(this, persist);
        }
    }

    /**
     * 设置过期时间
     * @param key         key
     * @param timeInMills 毫秒时间之后过期
     * @return this
     */
    @Override
    @SivanCacheInterceptor
    public ISivanCache<K, V> expire(K key, long timeInMills) {
        long expireTime = System.currentTimeMillis() + timeInMills;

        // 使用代理调用
        SivanCache<K,V> cachePoxy = (SivanCache<K, V>) SivanCacheProxy.getProxy(this);
        return cachePoxy.expireAt(key, expireTime);
    }

    /**
     * 指定过期信息
     * @param key key
     * @param timeInMills 时间戳
     * @return this
     */
    @Override
    @SivanCacheInterceptor(aof = true)
    public ISivanCache<K, V> expireAt(K key, long timeInMills) {
        this.expire.expire(key, timeInMills);
        return this;
    }

    @Override
    @SivanCacheInterceptor
    public ISivanCacheExpire<K, V> expire() {
        return this.expire;
    }

    @Override
    @SivanCacheInterceptor(refresh = true)
    public int size() {
        return map.size();
    }

    @Override
    @SivanCacheInterceptor(refresh = true)
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    @SivanCacheInterceptor(refresh = true, evict = true)
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    @SivanCacheInterceptor(refresh = true)
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    @SivanCacheInterceptor(evict = true)
    @SuppressWarnings("unchecked")
    public V get(Object key) {
        //1. 刷新所有过期信息
        K genericKey = (K) key;
        this.expire.refreshExpire(Collections.singletonList(genericKey));

        return map.get(key);
    }

    @Override
    @SivanCacheInterceptor(aof = true, evict = true)
    public V put(K key, V value) {
        //1.1 尝试驱除
        SivanCacheEvictContext<K,V> context = new SivanCacheEvictContext<>();
        context.key(key).size(sizeLimit).cache(this);

        ISivanCacheEntry<K,V> evictEntry = evict.evict(context);

        // 添加拦截器调用
        if(ObjectUtil.isNotNull(evictEntry)) {
            // 执行淘汰监听器
            ISivanCacheRemoveListenerContext<K,V> removeListenerContext = SivanCacheRemoveListenerContext.<K,V>newInstance().key(evictEntry.key())
                    .value(evictEntry.value())
                    .type(SivanCacheRemoveType.EVICT.code());
            for(ISivanCacheRemoveListener<K,V> listener : context.cache().removeListeners()) {
                listener.listen(removeListenerContext);
            }
        }

        //2. 判断驱除后的信息
        if(isSizeLimit()) {
            throw new SivanCacheRuntimeException("当前队列已满，数据添加失败！");
        }

        //3. 执行添加
        return map.put(key, value);
    }

    /**
     * 是否已经达到大小最大的限制
     * @return 是否限制
     *
     */
    private boolean isSizeLimit() {
        final int currentSize = this.size();
        return currentSize >= this.sizeLimit;
    }

    @Override
    @SivanCacheInterceptor(aof = true, evict = true)
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    @SivanCacheInterceptor(aof = true)
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    @SivanCacheInterceptor(refresh = true, aof = true)
    public void clear() {
        map.clear();
    }

    @Override
    @SivanCacheInterceptor(refresh = true)
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    @SivanCacheInterceptor(refresh = true)
    public Collection<V> values() {
        return map.values();
    }

    @Override
    @SivanCacheInterceptor(refresh = true)
    public Set<Map.Entry<K, V>> entrySet() {
        return map.entrySet();
    }

}
