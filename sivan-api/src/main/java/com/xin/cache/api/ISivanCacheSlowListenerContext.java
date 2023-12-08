package com.xin.cache.api;

/**
 * 慢日志监听器上下文
 *
 * @author sivan
 */
public interface ISivanCacheSlowListenerContext {

    /**
     * 方法名称
     * @return 方法名称
     */
    String methodName();

    /**
     * 参数信息
     * @return 参数列表
     */
    Object[] params();

    /**
     * 方法结果
     * @return 方法结果
     */
    Object result();

    /**
     * 开始时间
     * @return 时间
     */
    long startTimeMills();

    /**
     * 结束时间
     * @return 结束时间
     */
    long endTimeMills();

    /**
     * 消耗时间
     * @return 耗时
     */
    long costTimeMills();

}
