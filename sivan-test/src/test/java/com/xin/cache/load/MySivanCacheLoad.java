package com.xin.cache.load;

import com.xin.cache.api.ISivanCache;
import com.xin.cache.api.ISivanCacheLoad;

public class MySivanCacheLoad implements ISivanCacheLoad<String,String> {

    @Override
    public void load(ISivanCache<String, String> cache) {
        cache.put("1", "1");
        cache.put("2", "2");
    }

}
