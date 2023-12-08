package com.xin.cache.support.persist;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.xin.cache.api.ISivanCache;
import com.xin.cache.api.ISivanCachePersist;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 内部缓存持久化类
 * @author sivan
 * @param <K> key
 * @param <V> value
 *    
 */
public class InnerSivanCachePersist<K,V> {

    private static final Log log = LogFactory.getLog(InnerSivanCachePersist.class);

    /**
     * 缓存信息
     *    
     */
    private final ISivanCache<K,V> cache;

    /**
     * 缓存持久化策略
     *    
     */
    private final ISivanCachePersist<K,V> persist;

    /**
     * 线程执行类
     *    
     */
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    public InnerSivanCachePersist(ISivanCache<K, V> cache, ISivanCachePersist<K, V> persist) {
        this.cache = cache;
        this.persist = persist;

        // 初始化
        this.init();
    }

    /**
     * 初始化
     *    
     */
    private void init() {
        EXECUTOR_SERVICE.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("开始持久化缓存信息");
                    persist.persist(cache);
                    log.info("完成持久化缓存信息");
                } catch (Exception exception) {
                    log.error("文件持久化异常", exception);
                }
            }
        }, persist.delay(), persist.period(), persist.timeUnit());
    }

}
