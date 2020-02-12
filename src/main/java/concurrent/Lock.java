package concurrent;

/**
 * Created by xujia on 2019/5/29
 */
public class Lock {

    /**
     * 标识锁状态
     */
    private boolean isLocked;
    /**
     * 当前线程对象
     */
    private Thread thread;
    /**
     * 标识重入次数
     */
    private int count;

    /**
     * witf和notify方法必须与synchronized一同使用，为了避免某些场景，即：
     * 当不保证同步时，代表任意线程可以在任意时刻调用，线程1在还未执行wait方法时，线程2已经执行完notify了，此时线程1才执行wait
     * 如果没有其他线程再次执行notify，线程1将永远等待。
     *
     * 可以简单的认为synchronized锁对象维护了一个等待队列，当执行了wait方法将被放入等待队列中，当另一个线程执行notify时，会唤醒等待队列中的线程
     * @throws InterruptedException
     */
    public synchronized void lock() throws InterruptedException{
        Thread curThread = Thread.currentThread();
        while (isLocked && curThread != thread) {
            System.out.println(curThread.getName() + "调用wait()，被阻塞");
            wait();
        }
        count ++;
        isLocked = true;
        thread = curThread;
    }

    public synchronized void unlock() {
        if (Thread.currentThread() == thread) {
            count --;
            if (count == 0){
                isLocked = false;
                System.out.println(Thread.currentThread().getName() + "调用notify()，唤醒另外一个线程");
                notify();
            }
        }
    }

}
