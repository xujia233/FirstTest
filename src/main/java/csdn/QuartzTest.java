package csdn;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * quartz模拟异常情景
 * 情景描述：quartz规定某个任务每2s运行一次，但是该任务需要执行5s，此时会发生什么？
 * 如果是s的情况，在线程充足的情况下 则程序不会报错quartz运行的任务也会持续下去，工厂方法StdSchedulerFactory默认使用了一个ThreadExecutor的缺省实现，
 * 其最终所属线程池将维护10个线程，因此当启动第三个线程运行任务时，第一个线程已经完成任务了，除非说该任务执行的时间超级慢，在第十个线程启动运行
 * 任务之后第一个线程还未开始，此时程序不会报错，程序其他部分继续运行，quartz停止运行。
 * Created by xujia on 2019/6/6
 */
@Slf4j
public class QuartzTest {

    public static void main(String[] args) throws Exception{
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        // 获取调度器
        Scheduler scheduler = schedulerFactory.getScheduler();
        Trigger trigger= TriggerBuilder
                .newTrigger()
                .withIdentity("trigger_timed_period")
                // 开始执行时间
                .startAt(new Date())
                // CalendarIntervalScheduleBuilder defines calendar time (day, week, month, year) interval-based schedules for Trigger
                .withSchedule(CalendarIntervalScheduleBuilder.calendarIntervalSchedule()
                        // 每隔多长时间执行一次
                        .withInterval(3, DateBuilder.IntervalUnit.SECOND)
                )
                .build();
        JobDetail jobDetail = JobBuilder.newJob(QuartzJobTest.class).withIdentity("test").build();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }

}
