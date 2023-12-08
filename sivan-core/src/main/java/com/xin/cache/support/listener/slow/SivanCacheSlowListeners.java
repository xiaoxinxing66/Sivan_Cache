package com.xin.cache.support.listener.slow;


import com.xin.cache.api.ISivanCacheSlowListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 慢日志监听工具类
 * @author sivan
 *    
 */
public final class SivanCacheSlowListeners {

    private SivanCacheSlowListeners(){}

    /**
     * 无
     * @return 监听类列表
     *    
     */
    public static List<ISivanCacheSlowListener> none() {
        return new ArrayList<>();
    }

    /**
     * 默认实现
     * @return 默认
     *    
     */
    public static ISivanCacheSlowListener defaults() {
        return new SivanCacheSlowListener();
    }

}
