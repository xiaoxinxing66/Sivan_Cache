package com.xin.cache.support.expire;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import com.xin.cache.api.ISivanCache;
import com.xin.cache.api.ISivanCacheExpire;
import com.xin.cache.api.ISivanCacheRemoveListener;
import com.xin.cache.api.ISivanCacheRemoveListenerContext;
import com.xin.cache.constant.SivanCacheRemoveType;
import com.xin.cache.support.listener.remove.SivanCacheRemoveListenerContext;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 缓存过期-普通策略
 *
 * @author sivan
 * @param <K> key
 * @param <V> value
 */
public class SivanCacheExpire<K,V> implements ISivanCacheExpire<K,V> {

    /**
     * 单次清空的数量限制
     *    
     */
    private static final int LIMIT = 100;

    /**
     * 过期 map
     *
     * 空间换时间
     *    
     */
    private final Map<K, Long> expireMap = new HashMap<>();

    /**
     * 缓存实现
     *    
     */
    private final ISivanCache<K,V> cache;

    /**
     * 线程执行类
     *    
     */
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    public SivanCacheExpire(ISivanCache<K, V> cache) {
        this.cache = cache;
        this.init();
    }

    /**
     * 初始化任务
     *    
     */
    private void init() {
        EXECUTOR_SERVICE.scheduleAtFixedRate(new ExpireThread(), 100, 100, TimeUnit.MILLISECONDS);
    }

    /**
     * 定时执行任务
     *    
     */
    private class ExpireThread implements Runnable {
        @Override
        public void run() {
            //1.判断是否为空
            if(MapUtil.isEmpty(expireMap)) {
                return;
            }

            //2. 获取 key 进行处理
            int count = 0;
            for(Map.Entry<K, Long> entry : expireMap.entrySet()) {
                if(count >= LIMIT) {
                    return;
                }

                expireKey(entry.getKey(), entry.getValue());
                count++;
            }
        }
    }

    @Override
    public void expire(K key, long expireAt) {
        expireMap.put(key, expireAt);
    }

    @Override
    public void refreshExpire(Collection<K> keyList) {
        if(CollectionUtil.isEmpty(keyList)) {
            return;
        }

        // 判断大小，小的作为外循环。一般都是过期的 keys 比较小。
        if(keyList.size() <= expireMap.size()) {
            for(K key : keyList) {
                Long expireAt = expireMap.get(key);
                expireKey(key, expireAt);
            }
        } else {
            for(Map.Entry<K, Long> entry : expireMap.entrySet()) {
                this.expireKey(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public Long expireTime(K key) {
        return expireMap.get(key);
    }

    /**
     * 过期处理 key
     * @param key key
     * @param expireAt 过期时间
     *    
     */
    private void expireKey(final K key, final Long expireAt) {
        if(expireAt == null) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        if(currentTime >= expireAt) {
            expireMap.remove(key);
            // 再移除缓存，后续可以通过惰性删除做补偿
            V removeValue = cache.remove(key);

            // 执行淘汰监听器
            ISivanCacheRemoveListenerContext<K,V> removeListenerContext = SivanCacheRemoveListenerContext.<K,V>newInstance().key(key).value(removeValue).type(SivanCacheRemoveType.EXPIRE.code());
            for(ISivanCacheRemoveListener<K,V> listener : cache.removeListeners()) {
                listener.listen(removeListenerContext);
            }
        }
    }

}
