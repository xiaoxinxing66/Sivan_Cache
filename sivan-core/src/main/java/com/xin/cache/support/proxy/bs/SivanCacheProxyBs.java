package com.xin.cache.support.proxy.bs;


import com.xin.cache.annotation.SivanCacheInterceptor;
import com.xin.cache.api.ISivanCache;
import com.xin.cache.api.ISivanCacheInterceptor;
import com.xin.cache.api.ISivanCachePersist;
import com.xin.cache.support.interceptor.SivanCacheInterceptorContext;
import com.xin.cache.support.interceptor.SivanCacheInterceptors;
import com.xin.cache.support.persist.SivanCachePersistAof;

import java.util.List;

/**
 * 代理引导类
 * @author sivan
 *    
 */
public final class SivanCacheProxyBs {

    private SivanCacheProxyBs(){}

    /**
     * 代理上下文
     *    
     */
    private ISivanCacheProxyBsContext context;

    /**
     * 默认通用拦截器
     *
     * JDK 的泛型擦除导致这里不能使用泛型
     *    
     */
    @SuppressWarnings("all")
    private final List<ISivanCacheInterceptor> commonInterceptors = SivanCacheInterceptors.defaultCommonList();

    /**
     * 默认刷新拦截器
     *    
     */
    @SuppressWarnings("all")
    private final List<ISivanCacheInterceptor> refreshInterceptors = SivanCacheInterceptors.defaultRefreshList();

    /**
     * 持久化拦截器
     *   
     */
    @SuppressWarnings("all")
    private final ISivanCacheInterceptor persistInterceptors = SivanCacheInterceptors.aof();

    /**
     * 驱除拦截器
     *   
     */
    @SuppressWarnings("all")
    private final ISivanCacheInterceptor evictInterceptors = SivanCacheInterceptors.evict();

    /**
     * 新建对象实例
     * @return 实例
     *    
     */
    public static SivanCacheProxyBs newInstance() {
        return new SivanCacheProxyBs();
    }

    public SivanCacheProxyBs context(ISivanCacheProxyBsContext context) {
        this.context = context;
        return this;
    }

    /**
     * 执行
     * @return 结果
     *    
     * @throws Throwable 异常
     */
    @SuppressWarnings("all")
    public Object execute() throws Throwable {
        //1. 开始的时间
        final long startMills = System.currentTimeMillis();
        final ISivanCache cache = context.target();
        SivanCacheInterceptorContext interceptorContext = SivanCacheInterceptorContext.newInstance()
                .startMills(startMills)
                .method(context.method())
                .params(context.params())
                .cache(context.target())
                ;

        //1. 获取刷新注解信息
        SivanCacheInterceptor cacheInterceptor = context.interceptor();
        this.interceptorHandler(cacheInterceptor, interceptorContext, cache, true);

        //2. 正常执行
        Object result = context.process();

        final long endMills = System.currentTimeMillis();
        interceptorContext.endMills(endMills).result(result);

        // 方法执行完成
        this.interceptorHandler(cacheInterceptor, interceptorContext, cache, false);
        return result;
    }

    /**
     * 拦截器执行类
     * @param cacheInterceptor 缓存拦截器
     * @param interceptorContext 上下文
     * @param cache 缓存
     * @param before 是否执行执行
     *    
     */
    @SuppressWarnings("all")
    private void interceptorHandler(SivanCacheInterceptor cacheInterceptor,
                                    SivanCacheInterceptorContext interceptorContext,
                                    ISivanCache cache,
                                    boolean before) {
        if(cacheInterceptor != null) {
            //1. 通用
            if(cacheInterceptor.common()) {
                for(ISivanCacheInterceptor interceptor : commonInterceptors) {
                    if(before) {
                        interceptor.before(interceptorContext);
                    } else {
                        interceptor.after(interceptorContext);
                    }
                }
            }

            //2. 刷新
            if(cacheInterceptor.refresh()) {
                for(ISivanCacheInterceptor interceptor : refreshInterceptors) {
                    if(before) {
                        interceptor.before(interceptorContext);
                    } else {
                        interceptor.after(interceptorContext);
                    }
                }
            }

            //3. AOF 追加
            final ISivanCachePersist cachePersist = cache.persist();
            if(cacheInterceptor.aof() && (cachePersist instanceof SivanCachePersistAof)) {
                if(before) {
                    persistInterceptors.before(interceptorContext);
                } else {
                    persistInterceptors.after(interceptorContext);
                }
            }

            //4. 驱除策略更新
            if(cacheInterceptor.evict()) {
                if(before) {
                    evictInterceptors.before(interceptorContext);
                } else {
                    evictInterceptors.after(interceptorContext);
                }
            }
        }
    }

}
