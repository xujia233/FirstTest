package concurrent;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * fork/join 测试
 * Created by xujia on 2019/5/30
 */
public class ForkJoinTest  {

    @Test
    public void test() {
        long start = System.currentTimeMillis();
        CountTask countTask = new CountTask(1, 1000);
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        int sum = forkJoinPool.invoke(countTask);
        System.out.println("总数为:" + sum + ",耗费时间为:" + (System.currentTimeMillis() - start) + "ms");

    }

    class CountTask extends RecursiveTask<Integer> {
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
        protected Integer compute() {
            // 存放结果
            int sum = 0;
            // 第一步：根据业务需求参数递归拆分任务
            if ((end - start) <= COUNT_LENGTH) {
                // 无需拆分，直接计算
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
                System.out.println("当前线程正在执行:" + Thread.currentThread().getName() + ",start :" + start + ",end :" + end + ",sum:" + sum);
            } else {
                System.out.println(start + "到" + end + "的和的任务正在被拆分：拆分的线程为：" + Thread.currentThread().getName());
                // 需要拆分任务，拆成左右两个
                int mid = (end + start) / 2;
                CountTask left = new CountTask(start, mid);
                CountTask right = new CountTask(mid + 1, end);
                // 最好调用invokeAll方法来代替每个子任务的fork方法，fork方法存在先后顺序，而invokeAll同时干活不会浪费线程，可以更好的利用线程池，降低执行时间
                invokeAll(left, right);
                // 第二步：整合每个任务的结果
                int leftResult = left.join();
                int rightResult = right.join();
                sum = leftResult + rightResult;
            }
            return sum;
        }
    }

}
