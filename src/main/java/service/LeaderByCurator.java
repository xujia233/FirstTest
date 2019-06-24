package service;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 基于curator库的分布式锁，利用Zookeeper的互斥锁实现，即临时有序节点实现，每创建临时节点都会赋予一个序号值并以此递增，序号最小
 * 的节点则为持锁者，每个节点都只监听比它稍小的节点，例如2监听1，3监听2，当某个节点失去连接时Zookeeper会自动清除该节点并通知比它
 * 序号大的那一个节点
 * cuartor获取锁的内部实现：内部维护了一个计数器用于重入及当前获取锁对象的缓存map，主要也是利用Zookeeper临时顺序节点的特性，其获取释放锁主要是通过
 * notifyAll和wait机制来实现。
 * Created by xujia on 2019/6/10
 */
public class LeaderByCurator {

    /**
     * 集群以逗号隔开
     */
    private static final String ZK_ADDRESS = "10.1.61.165:2181";
    private static final String ZK_LOCK_PATH = "/zktest/lock0";

    public static void main(String[] args) throws InterruptedException {
        // 首先连接zk RetryNTimes：指定重连次数的重试策略，这里是重连10次，每次间隔5s
        final CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));
        client.start();

        System.out.println(client.getState() + "zk client start successfully!");

        final InterProcessMutex mutex = new InterProcessMutex(client, ZK_LOCK_PATH);

        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(() -> doWithLock(client, mutex), "Thread-" + i);
            thread.start();
        }
    }

    private static void doWithLock(CuratorFramework client, InterProcessMutex mutex) {
        try {
            String name = Thread.currentThread().getName();
            // acquire方法支持同线程重入，获取不到锁会一直阻塞，这里为了测试同时设置超时时间3s  <一般实际开发时不需要指定超时时长，不符合分布式锁业务需求>
            if (mutex.acquire(3, TimeUnit.SECONDS)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println(simpleDateFormat.format(new Date()) + "," + name + " hold lock");

                System.out.println(client.getChildren().forPath(ZK_LOCK_PATH));

                Thread.sleep(1000);
                System.out.println(simpleDateFormat.format(new Date()) + "," + name + " release lock");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 当设置的超时时间小于线程执行时间时，其余线程在阻塞等待超时时间后尝试去获取锁时获取失败，删除对应节点，不竞争下次锁直接返回并抛出异常。
            try {
                mutex.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
