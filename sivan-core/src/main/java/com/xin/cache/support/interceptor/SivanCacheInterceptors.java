package com.xin.cache.support.interceptor;


import com.xin.cache.api.ISivanCacheInterceptor;
import com.xin.cache.support.interceptor.aof.SivanCacheInterceptorAof;
import com.xin.cache.support.interceptor.common.SivanCacheInterceptorCost;
import com.xin.cache.support.interceptor.evict.SivanCacheInterceptorEvict;
import com.xin.cache.support.interceptor.refresh.SivanCacheInterceptorRefresh;

import java.util.ArrayList;
import java.util.List;

/**
 * 缓存拦截器工具类
 * @author sivan
 *    
 */
public final class SivanCacheInterceptors {

    /**
     * 默认通用
     * @return 结果
     *    
     */
    @SuppressWarnings("all")
    public static List<ISivanCacheInterceptor> defaultCommonList() {
        List<ISivanCacheInterceptor> list = new ArrayList<>();
        list.add(new SivanCacheInterceptorCost());
        return list;
    }

    /**
     * 默认刷新
     * @return 结果
     *    
     */
    @SuppressWarnings("all")
    public static List<ISivanCacheInterceptor> defaultRefreshList() {
        List<ISivanCacheInterceptor> list = new ArrayList<>();
        list.add(new SivanCacheInterceptorRefresh());
        return list;
    }

    /**
     * AOF 模式
     * @return 结果
     *   
     */
    @SuppressWarnings("all")
    public static ISivanCacheInterceptor aof() {
        return new SivanCacheInterceptorAof();
    }

    /**
     * 驱除策略拦截器
     * @return 结果
     *   
     */
    @SuppressWarnings("all")
    public static ISivanCacheInterceptor evict() {
        return new SivanCacheInterceptorEvict();
    }

}
