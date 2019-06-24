package concurrent;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by xujia on 2019/5/29
 */
public class CounterTest {

    private CountDownLatch countDownLatch;

    @Test
    public void test() {
        // 1、初始化计数器并设置等待线程数
        countDownLatch = new CountDownLatch(2);
        new ThreadTest().start();
        new ThreadTest().start();
        try {
            System.out.println("等待2个子线程执行完毕");
            // 3、阻塞等待，直到计数器为0
            countDownLatch.await();
            // 下面的业务逻辑需要等到上面多线程全部执行完毕之后才能执行
            System.out.println("2个子线程执行完毕");
            System.out.println("执行主线程");
        } catch (Exception e) {

        }
    }

    class ThreadTest extends Thread{

        @Override
        public void run() {
            try {
                System.out.println("线程" + Thread.currentThread().getName() + "正在执行");
                Thread.sleep(1500);
                System.out.println("线程" + Thread.currentThread().getName() + "执行完毕");
            } catch (Exception e) {

            } finally {
                // 2、每完成一个线程，需调用该方法，计数器减1
                countDownLatch.countDown();
            }
        }

    }
}
