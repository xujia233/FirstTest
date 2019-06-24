package concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 基于自旋锁
 * Created by xujia on 2019/5/29
 */
public class SpinLock {

    /**
     * 使用原子引用来存放线程
     */
    private AtomicReference<Thread> owner = new AtomicReference<>();
    /**
     * 存放重入次数即计数器
     */
    private int count;

    public void lock() {
        Thread curThread = Thread.currentThread();
        // 与Lock类一样判断是否是当前线程，如果是则直接退出
        if (curThread == owner.get()) {
            count ++;
            return;
        }
        // 基于CAS算法，如果null == owner，则将owner设为当前线程即默认取得锁，否则无限循环不断的判断是否能拿锁
        while (!owner.compareAndSet(null, curThread)) {
            // do nothing
        }
    }

    public void unlock() {
        Thread curThread = Thread.currentThread();
        if (curThread == owner.get()) {
            count --;
            if (count == 0) {
                owner.compareAndSet(curThread, null);
            }
        }
    }
}
