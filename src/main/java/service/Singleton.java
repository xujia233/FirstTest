package service;

/**
 * 单例模式 懒汉式单例：在第一次调用时实例化自己，线程不安全，并发环境下很可能出现多个Singleton实例
 * Created by xujia on 2019/2/14
 */
public class Singleton {

    private static Singleton singleton = null;

    public static Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }
}

/**
 * 懒汉式单例  在getInstance方法上加同步
 */
class Singleton1 {

    private static Singleton1 singleton = null;

    public static synchronized Singleton1 getInstance() {
        if (singleton == null) {
            singleton = new Singleton1();
        }
        return singleton;
    }
}

/**
 * 懒汉式单例 双重检查锁定
 */
class Singleton2 {

    private static Singleton2 singleton = null;

    public static Singleton2 getInstance() {
        if (singleton == null) {
            synchronized (Singleton2.class) {
                if (singleton == null)
                    singleton = new Singleton2();
            }
        }
        return singleton;
    }
}

/**
 * 饿汉式单例 在类初始化时实例化自己，线程安全
 */
class Singleton3 {

    private static final Singleton3 singleton = new Singleton3();

    public static Singleton3 getInstance() {
        return singleton;
    }
}
