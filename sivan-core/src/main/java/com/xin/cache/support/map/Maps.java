package com.xin.cache.support.map;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sivan
 *    
 */
public final class Maps {

    private Maps(){}

    /**
     * hashMap 实现策略
     * @param <K> key
     * @param <V> value
     * @return map 实现
     *    
     */
    public static <K,V> Map<K,V> hashMap() {
        return new HashMap<>();
    }

}
