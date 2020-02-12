package concurrent;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * 使用CompletableFuture最大的好处就是在任务完成后执行另一任务时不需要阻塞等待上一结果再开始，可以纯异步执行
 * Created by xujia on 2019/11/21
 */
public class ConcurrentTest {

    @Test
    public void test() throws Exception{
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("start");
            System.out.println("任务运行时当前线程:" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end");
            return 10;
        });
        // 完成时处理，whenComplete方法意味着使用和任务运行相同的线程，以Async结尾的方法会尝试使用其他的线程去执行，如果是同一个线程池的话，也有可能是同一个线程
        // 返回的future本质是任务的future
        CompletableFuture<Integer> fu = future.whenCompleteAsync((s, e) -> {
            System.out.println("任务完成后当前线程:" + Thread.currentThread().getName());
            System.out.println(s);
            System.out.println(e);
        });
//        System.out.println(future.get());
//        System.out.println(fu.get());
        System.in.read();
    }
}
