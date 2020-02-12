package concurrent;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 限流测试
 * Created by xujia on 2019/11/14
 */
public class LimitingTest {

    public static void main(String[] args) throws Exception{
        // 模拟同个ip一秒内多次发送请求
        while (true) {
            Thread.sleep(100);
            login("10.1.240.96");
        }
    }

    public static LoadingCache<String, RateLimiter> caches = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String key) throws Exception {
                    // 新的ip初始化，限流每秒两个令牌响应
                    return RateLimiter.create(2);
                }
            });

    public static void login(String ip) throws Exception{
        // 模拟ip的key
        RateLimiter rateLimiter = caches.get(ip);
        if (rateLimiter.tryAcquire()) {
            System.out.println("ip:" + ip + " success " + new SimpleDateFormat("HH:mm:ss.sss").format(new Date()));
        } else {
            System.out.println("ip:" + ip + " failed " + new SimpleDateFormat("HH:mm:ss.sss").format(new Date()));
        }
    }
}
