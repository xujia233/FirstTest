package notify;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例模式，简单来说就是一个类中只有一个实例，并提供获取该实例的静态方法
 * Created by xujia on 2019/5/25
 */
public class Singleton {

    private Singleton() {}

    private static class SingletonLayzer {
        private static Singleton singleton = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonLayzer.singleton;
    }


}
