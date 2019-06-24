package concurrent;

import org.junit.Test;

/**
 * Created by xujia on 2019/5/29
 */
public class People {

    //private SpinLock lock = new SpinLock();
    private Lock lock = new Lock();

    public void say() throws InterruptedException{
        lock.lock();
        System.out.println("i'm saying");
        eat();
        lock.unlock();
    }

    public void eat() throws InterruptedException{
        lock.lock();
        Thread.sleep(1000);
        System.out.println("i'm eating");
        lock.unlock();
    }

    @Test
    public void test() {
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName());
                say();
            } catch (Exception e) {

            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName());
                say();
            } catch (Exception e) {

            }
        }).start();
        try {
            Thread.sleep(5000);
        } catch (Exception e) {

        }
    }
}
