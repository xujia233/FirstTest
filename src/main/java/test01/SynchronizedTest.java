package test01;

/**
 * 测试synchronized作用在静态方法与非静态方法的区别
 * 前者是类锁，后者是对象锁
 * Created by xujia on 2019/8/21
 */
public class SynchronizedTest {

    /**
     * 控制同一个静态对象
     */
    public static SynchronizedTest synchronizedTest = new SynchronizedTest();

    /**
     * 非静态方法加锁1
     */
    public synchronized void method1() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Current thread:" + Thread.currentThread().getName() + ", method1 is running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 非静态方法加锁2
     */
    public synchronized void method2() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Current thread:" + Thread.currentThread().getName() + ", method2 is running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 静态方法加锁1
     */
    public static synchronized void staticMethod1() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Current thread:" + Thread.currentThread().getName() + ", staticMethod1 is running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 静态方法加锁2
     */
    public static synchronized void staticMethod2() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Current thread:" + Thread.currentThread().getName() + ", staticMethod2 is running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试主方法
     * @param args
     */
    public static void main(String[] args) {
        new Thread(new Thread1()).start();
        new Thread(new Thread2()).start();
    }

}

/**
 * 两个测试线程，模拟不同锁方法的获取锁示例，两者一行一行对应模拟不同的场景
 * 通过释放注释来达到不同的效果
 */
class Thread1 implements Runnable {
    @Override
    public void run() {
        // 第一个场景：同一个对象调用同一个非静态方法  结果：发生互斥，synchronized锁的是当前对象，因此在访问该对象的任何同步方法时一定是互斥的
        // SynchronizedTest.synchronizedTest.method1();

        // 第二个场景：同一个对象调用两个不同的非静态方法  结果：发生互斥，同第一个场景，注意非静态方法是对象锁。
        // SynchronizedTest.synchronizedTest.method2();

        // 第三个场景：不同对象分别调用同一个非静态方法   结果：不会互斥，因为是两个不同的对象，锁的主体都不同了
        // new SynchronizedTest().method1();

        // 第四个场景：不限制同一对象，分别调用非静态方法与静态方法  结果：不会互斥，静态方法由于锁的是当前类对象即class对象，而非静态方法锁的是当前
        // 对象即this对象，两者锁的类型不一样，换句话说调用的主体也不一样，静态方法实际上是类对象在调用，因此两者并不会互斥，会并发进行
        // SynchronizedTest.synchronizedTest.method2();

        // 第五个场景：使用类直接调用两个不同的静态方法   结果：发生互斥，因为两个线程调用的都是静态方法，而静态方法锁的是当前类对象，而class
        // 只有一份，可以理解为任何时候只有一把钥匙，不管多少人想进这个class对象空间，都需要排队拿钥匙
        // SynchronizedTest.staticMethod1();

        // 第六个场景：从第四个场景衍生，使用同一个对象分别调用非静态方法与静态方法 结果：不会互斥，还是因为锁的是不同类型，与同一对象无关
        // ps：调用静态方法时不要通过静态对象去调用，使用类实例即可，这边只是为了测试...
        SynchronizedTest.synchronizedTest.method2();
    }
}

class Thread2 implements Runnable {
    @Override
    public void run() {
        // 一
        // SynchronizedTest.synchronizedTest.method1();

        // 二
        // SynchronizedTest.synchronizedTest.method1();

        // 三
        // new SynchronizedTest().method1();

        // 四
        // SynchronizedTest.staticMethod1();

        // 五
        // SynchronizedTest.staticMethod2();

        // 六
        SynchronizedTest.synchronizedTest.staticMethod1();
    }
}