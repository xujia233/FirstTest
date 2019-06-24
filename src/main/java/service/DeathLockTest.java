package service;

import entity.LockTest;
import org.junit.Test;

/**
 * 模拟死锁场景：两个线程，你拿了我的锁，我拿了你的锁，互不相让产生死锁
 * Created by xujia on 2019/2/14
 */
public class DeathLockTest {

    class ThreadTest implements Runnable {

        public boolean flag;

        public ThreadTest(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            if (flag) {
                while (true) {
                    synchronized (LockTest.lockA) {
                        System.out.println("thread name : " + Thread.currentThread().getName() + ", locka...");
                        synchronized (LockTest.lockB) {
                            System.out.println("thread name : " + Thread.currentThread().getName() + ", lockb...");
                        }
                    }
                }
            } else {
                while (true) {
                    synchronized (LockTest.lockB) {
                        System.out.println("thread name : " + Thread.currentThread().getName() + ", lockb...");
                        synchronized (LockTest.lockA) {
                            System.out.println("thread name : " + Thread.currentThread().getName() + ", locka...");
                        }
                    }
                }
            }
        }
    }

    @Test
    public void test01() {
        Thread thread1 = new Thread(new ThreadTest(true));
        Thread thread2 = new Thread(new ThreadTest(false));
        thread1.start();
        thread2.start();
    }
}
