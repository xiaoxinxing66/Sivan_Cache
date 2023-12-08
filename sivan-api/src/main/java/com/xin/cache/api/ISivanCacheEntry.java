package com.xin.cache.api;

public interface ISivanCacheEntry<K, V> {

    K key();
    V value();

}