package com.xin.cache.support.listener.slow;


import com.xin.cache.api.ISivanCacheSlowListenerContext;

/**
 * @author sivan
 *    
 */
public class SivanCacheSlowListenerContext implements ISivanCacheSlowListenerContext {

    /**
     * 方法名称
     *    
     */
    private String methodName;

    /**
     * 参数信息
     *    
     */
    private Object[] params;

    /**
     * 方法结果
     *    
     */
    private Object result;

    /**
     * 开始时间
     *    
     */
    private long startTimeMills;

    /**
     * 结束时间
     *    
     */
    private long endTimeMills;

    /**
     * 消耗时间
     *    
     */
    private long costTimeMills;

    /**
     *    
     * @return 实例
     */
    public static SivanCacheSlowListenerContext newInstance() {
        return new SivanCacheSlowListenerContext();
    }

    @Override
    public String methodName() {
        return methodName;
    }

    public SivanCacheSlowListenerContext methodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    @Override
    public Object[] params() {
        return params;
    }

    public SivanCacheSlowListenerContext params(Object[] params) {
        this.params = params;
        return this;
    }

    @Override
    public Object result() {
        return result;
    }

    public SivanCacheSlowListenerContext result(Object result) {
        this.result = result;
        return this;
    }

    @Override
    public long startTimeMills() {
        return startTimeMills;
    }

    public SivanCacheSlowListenerContext startTimeMills(long startTimeMills) {
        this.startTimeMills = startTimeMills;
        return this;
    }

    @Override
    public long endTimeMills() {
        return endTimeMills;
    }

    public SivanCacheSlowListenerContext endTimeMills(long endTimeMills) {
        this.endTimeMills = endTimeMills;
        return this;
    }

    @Override
    public long costTimeMills() {
        return costTimeMills;
    }

    public SivanCacheSlowListenerContext costTimeMills(long costTimeMills) {
        this.costTimeMills = costTimeMills;
        return this;
    }
}
