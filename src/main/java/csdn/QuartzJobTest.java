package csdn;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xujia on 2019/6/6
 */
public class QuartzJobTest implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"执行为5m，模拟单个线程执行时间超过quartz调度周期3s" + "当前线程未：" +
                    Thread.currentThread().getName());
            // 睡眠5m，使之超出quartz的调度周期
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
    }
}
