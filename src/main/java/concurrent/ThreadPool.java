package concurrent;

import org.junit.Test;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 普通线程池测试
 * Created by xujia on 2019/5/30
 */
public class ThreadPool implements Serializable {

    // 固定大小的线程池，大小为当前机子cpu数量的两倍或者是cpu数+1，根据线程所执行任务的不同而不同
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    @Test
    public void test() {
        CountTask countTask = new CountTask(1, 1000);
        // 提交主任务并输出结果
        Future<Integer> result = executorService.submit(countTask);
        try {
            System.out.println("总数为：" + result.get());
        } catch (Exception e) {

        }
    }

    class CountTask implements Callable<Integer> {

        /**
         * 开始计算点
         */
        private int start;
        /**
         * 结束计算点
         */
        private int end;

        /**
         * 每个任务计算的最大阈值
         */
        private static final int COUNT_LENGTH = 50;

        public CountTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public Integer call() throws Exception {
            int sum = 0;
            if (end -start <= COUNT_LENGTH) {
                // 无需拆分
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                System.out.println("正在进行拆分任务的线程为：" + Thread.currentThread().getName());
                // 拆分成左右两个子任务
                int mid = (end + start) / 2;
                CountTask left = new CountTask(start, mid);
                CountTask right = new CountTask(mid + 1, end);
                // 提交到线程池中允许
                Future<Integer> leftResult = executorService.submit(left);
                Future<Integer> rightResult = executorService.submit(right);
                // 合并结果
                sum = leftResult.get() + rightResult.get();
            }
            return sum;
        }
    }

    @Test
    public void test12() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        executorService.submit(() ->{
            try {
                Thread.sleep(3000);
                System.out.println("异步线程执行完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // 平滑的关闭线程池，线程池不再接受新的任务，等待已经提交的线程执行完成（包括提交正在执行以及提交未执行）便关闭
        executorService.shutdown();


        System.out.println("主线程执行完");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
