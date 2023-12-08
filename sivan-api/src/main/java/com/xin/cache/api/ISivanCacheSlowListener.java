package com.xin.cache.api;

/**
 * 慢日志操作接口
 *
 * @author sivan
 */
public interface ISivanCacheSlowListener {

    /**
     * 监听
     * @param context 上下文
     */
    void listen(final ISivanCacheSlowListenerContext context);

    /**
     * 慢日志的阈值
     * @return 慢日志的阈值
     */
    long slowerThanMills();

}
