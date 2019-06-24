package service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存测试类
 * Created by xujia on 2019/6/13
 */
public class LoadingCacheTest {

    private static LoadingCache<String, Map<String, String>> cache;

    {
        cache = CacheBuilder.newBuilder()
                // 5min后失效，该方法在缓存失效后，若同一时刻有多个线程来获取，只会有一个线程进行加载数据源，其余线程均会阻塞等待
                .expireAfterWrite(5, TimeUnit.MINUTES)
                // 最多可以存1000个缓存项
                .maximumSize(1000)
                .build(new CacheLoader<String, Map<String, String>>() {
                    @Override
                    public Map<String, String> load(String key) throws Exception {
                        return null;
                    }
                });
    }

}
