package com.xin.cache.bs;


import com.xin.cache.api.ISivanCache;
import com.xin.cache.listener.MyRemoveListener;
import com.xin.cache.listener.MySlowListener;
import com.xin.cache.load.MySivanCacheLoad;
import com.xin.cache.support.evict.SivanCacheEvicts;
import com.xin.cache.support.load.SivanCacheLoads;
import com.xin.cache.support.map.Maps;
import com.xin.cache.support.persist.SivanCachePersists;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 缓存引导类测试
 * @author sivan
 *  
 */
public class SivanCacheBsTest {

    /**
     * 大小指定测试
     *  
     */
    @Test
    public void helloTest() {
        ISivanCache<String, String> cache = SivanCacheBs.<String,String>newInstance()
                .size(2)
                .build();

        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        cache.put("4", "4");

        Assert.assertEquals(2, cache.size());
        System.out.println(cache.keySet());
    }

    /**
     * 配置指定测试
     *  
     */
    @Test
    public void configTest() {
        ISivanCache<String, String> cache = SivanCacheBs.<String,String>newInstance()
                .map(Maps.<String,String>hashMap())
                .evict(SivanCacheEvicts.<String, String>fifo())
                .size(2)
                .build();

        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        cache.put("4", "4");

        Assert.assertEquals(2, cache.size());
        System.out.println(cache.keySet());
    }

    /**
     * 过期测试
     *    
     */
    @Test
    public void expireTest() throws InterruptedException {
        ISivanCache<String, String> cache = SivanCacheBs.<String,String>newInstance()
                .size(3)
                .build();

        cache.put("1", "1");
        cache.put("2", "2");

        cache.expire("1", 40);
        Assert.assertEquals(2, cache.size());

        TimeUnit.MILLISECONDS.sleep(50);
        Assert.assertEquals(1, cache.size());
        System.out.println(cache.keySet());
    }

    /**
     * 缓存删除监听器
     *    
     */
    @Test
    public void cacheRemoveListenerTest() {
        ISivanCache<String, String> cache = SivanCacheBs.<String,String>newInstance()
                .size(1)
                .addRemoveListener(new MyRemoveListener<String, String>())
                .build();

        cache.put("1", "1");
        cache.put("2", "2");
    }

    /**
     * 加载接口测试
     *    
     */
    @Test
    public void loadTest() {
        ISivanCache<String, String> cache = SivanCacheBs.<String,String>newInstance()
                .load(new MySivanCacheLoad())
                .build();

        Assert.assertEquals(2, cache.size());
    }

    /**
     * 持久化接口测试
     *    
     */
    @Test
    public void persistRdbTest() throws InterruptedException {
        ISivanCache<String, String> cache = SivanCacheBs.<String,String>newInstance()
                .load(new MySivanCacheLoad())
                .persist(SivanCachePersists.<String, String>dbJson("1.rdb"))
                .build();

        Assert.assertEquals(2, cache.size());
        TimeUnit.SECONDS.sleep(5);
    }

    /**
     * 加载接口测试
     *    
     */
    @Test
    public void loadDbJsonTest() {
        ISivanCache<String, String> cache = SivanCacheBs.<String,String>newInstance()
                .load(SivanCacheLoads.<String, String>dbJson("1.rdb"))
                .build();

        Assert.assertEquals(2, cache.size());
    }

    /**
     * 慢日志接口测试
     *    
     */
    @Test
    public void slowLogTest() {
        ISivanCache<String, String> cache = SivanCacheBs.<String,String>newInstance()
                .addSlowListener(new MySlowListener())
                .build();

        cache.put("1", "2");
        cache.get("1");
    }


    /**
     * 持久化 AOF 接口测试
     *   
     */
    @Test
    public void persistAofTest() throws InterruptedException {
        ISivanCache<String, String> cache = SivanCacheBs.<String,String>newInstance()
                .persist(SivanCachePersists.<String, String>aof("1.aof"))
                .build();

        cache.put("1", "1");
        cache.expire("1", 10);
        cache.remove("2");

        TimeUnit.SECONDS.sleep(1);
    }

    /**
     * 加载 AOF 接口测试
     *   
     */
    @Test
    public void loadAofTest() throws InterruptedException {
        ISivanCache<String, String> cache = SivanCacheBs.<String,String>newInstance()
                .load(SivanCacheLoads.<String, String>aof("default.aof"))
                .build();

        Assert.assertEquals(1, cache.size());
        System.out.println(cache.keySet());
    }


    /**
     * LRU 驱除策略测试
     *   
     */
    @Test
    public void lruEvictTest() throws InterruptedException {
        ISivanCache<String, String> cache = SivanCacheBs.<String,String>newInstance()
                .size(3)
                .evict(SivanCacheEvicts.<String, String>lru())
                .build();

        cache.put("A", "hello");
        cache.put("B", "world");
        cache.put("C", "FIFO");

        // 访问一次A
        cache.get("A");
        cache.put("D", "LRU");

        Assert.assertEquals(3, cache.size());
        System.out.println(cache.keySet());
    }

    @Test
    public void lruDoubleListMapTest() throws InterruptedException {
        ISivanCache<String, String> cache = SivanCacheBs.<String,String>newInstance()
                .size(3)
                .evict(SivanCacheEvicts.<String, String>lruDoubleListMap())
                .build();

        cache.put("A", "hello");
        cache.put("B", "world");
        cache.put("C", "FIFO");

        // 访问一次A
        cache.get("A");
        cache.put("D", "LRU");

        Assert.assertEquals(3, cache.size());
        System.out.println(cache.keySet());
    }

    /**
     * 基于 LinkedHashMap 实现
     *   
     */
    @Test
    public void lruLinkedHashMapTest()  {
        ISivanCache<String, String> cache = SivanCacheBs.<String,String>newInstance()
                .size(3)
                .evict(SivanCacheEvicts.<String, String>lruLinkedHashMap())
                .build();

        cache.put("A", "hello");
        cache.put("B", "world");
        cache.put("C", "FIFO");

        // 访问一次A
        cache.get("A");
        cache.put("D", "LRU");

        Assert.assertEquals(3, cache.size());
        System.out.println(cache.keySet());
    }

    /**
     * 基于 LRU 2Q 实现
     *   
     */
    @Test
    public void lruQ2Test()  {
        ISivanCache<String, String> cache = SivanCacheBs.<String,String>newInstance()
                .size(3)
                .evict(SivanCacheEvicts.<String, String>lru2Q())
                .build();

        cache.put("A", "hello");
        cache.put("B", "world");
        cache.put("C", "FIFO");

        // 访问一次A
        cache.get("A");
        cache.put("D", "LRU");

        Assert.assertEquals(3, cache.size());
        System.out.println(cache.keySet());
    }

    /**
     * 基于 LRU-2 实现
     *   
     */
    @Test
    public void lru2Test()  {
        ISivanCache<String, String> cache = SivanCacheBs.<String,String>newInstance()
                .size(3)
                .evict(SivanCacheEvicts.<String, String>lru2())
                .build();

        cache.put("A", "hello");
        cache.put("B", "world");
        cache.put("C", "FIFO");

        // 访问一次A
        cache.get("A");
        cache.put("D", "LRU");

        Assert.assertEquals(3, cache.size());
        System.out.println(cache.keySet());
    }

    /**
     * 基于 LFU 实现
     *   
     */
    @Test
    public void lfuTest()  {
        ISivanCache<String, String> cache = SivanCacheBs.<String,String>newInstance()
                .size(3)
                .evict(SivanCacheEvicts.<String, String>lfu())
                .build();

        cache.put("A", "hello");
        cache.put("B", "world");
        cache.put("C", "FIFO");

        // 访问一次A
        cache.get("A");
        cache.put("D", "LRU");

        Assert.assertEquals(3, cache.size());
        System.out.println(cache.keySet());
    }


    /**
     * 基于 clock 算法 实现
     *   
     */
    @Test
    public void clockTest()  {
        ISivanCache<String, String> cache = SivanCacheBs.<String,String>newInstance()
                .size(3)
                .evict(SivanCacheEvicts.<String, String>clock())
                .build();

        cache.put("A", "hello");
        cache.put("B", "world");
        cache.put("C", "FIFO");

        // 访问一次A
        cache.get("A");
        cache.put("D", "LRU");

        Assert.assertEquals(3, cache.size());
        System.out.println(cache.keySet());
    }

}
