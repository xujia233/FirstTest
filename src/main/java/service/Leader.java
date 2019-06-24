package service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

/**
 * 分布式锁的使用场景：分布式系统下某些写操作（类似于初始化任务、mq监听任务、定时任务等等）
 * 基于zkclient实现分布式锁（即选主），利用zookeeper的简单互斥锁特性
 * 该方法只适用于节点较少，否则由于简单互斥锁的特性，每次抢锁都会造成大量的进程去竞争，并且当主锁挂掉时，zk会发通知给其余所有进程：告诉他们是时候来抢锁啦，这里的抢锁和通知过程即zk中的羊群效应
 * 所以在一般分布式系统中，如果使用zk来实现分布式锁都是采用互斥锁的特性，即有序节点的方式，而不是该简单方法，本文不作讨论
 * Created by xujia on 2019/4/16
 */
@Slf4j
public class Leader {

    /**
     * 分布式系统保证一个主节点，外层业务可根据该值判断
     */
    private volatile boolean isLeader = false;
    /**
     * zk客户端
     */
    private ZkClient zkClient;
    /**
     * 临时节点的路径，不要与其他业务逻辑重复
     */
    private String path;
    /**
     * 节点信息
     */
    private String hostInfo;
    /**
     * 根节点
     */
    private static final String ROOT_CATALOG = "/xj";

    /**
     * 初始化根节点
     */
    public void initRootCatlog() {
        if (zkClient.exists(ROOT_CATALOG))
            return;
        try {
            // 创建持久化节点
            zkClient.createPersistent(ROOT_CATALOG);
        } catch (Exception e) {
            log.warn("create root catalog error :", e);
        }
    }

    public Leader (String zkServers, String tempPath, String host) {
        // 第一个参数zkServers：zk地址，集群以逗号隔开  第二个参数sessionTimeout：会话超时时长   第三个参数connectionTimeout：连接超时时长
        zkClient = new ZkClient(new ZkConnection(zkServers, 10000), 10000);
        this.path = ROOT_CATALOG + tempPath;
        try {
            // 获取当前主机信息
            hostInfo = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            log.warn("get local host info error :", e);
        }
        // 为了测试，写死
        hostInfo = host;
        // 初始化根节点
        initRootCatlog();
        // 抢锁
        tryLeader();
        // 监听指定节点数据变化
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            /**
             * 节点数据改变时触发，该接口只会对所监控的路径的数据变化，子节点数据发送变化不会被监控到，例如s为/xj，若/xj/chile路径上的数据发生变化了则不会被监控到
             * @param path 节点路径
             * @param data 节点数据
             * @throws Exception
             */
            public void handleDataChange(String path, Object data) throws Exception {
                // 数据改变 无需重新获取主锁
            }

            /**
             * 节点删除时触发
             * @param path 节点路径
             * @throws Exception
             */
            public void handleDataDeleted(String path) throws Exception {
                // 主节点挂了，其余阻塞节点进入该方法尝试获取锁
                if (log.isInfoEnabled())
                    log.info(hostInfo + "start to get lock on :" + path);
                tryLeader();
            }
        });
    }

    /**
     * 获取主锁，即选举leader
     */
    public void tryLeader() {
        if (!zkClient.exists(path)) {
            try {
                // 创建临时节点，当同一时刻多个进程同时创建时，Zookeeper能保证只有一个进程会创建成功，此时则为持有主锁
                zkClient.createEphemeral(path, hostInfo);
                isLeader = true;
                if (log.isInfoEnabled())
                    log.info(hostInfo + "become leader success on :" + path);
            } catch (Exception e) {
                isLeader = false;
                if (log.isInfoEnabled())
                    log.info(hostInfo + "become leader failed on :" + path);
            }
        }
        if (log.isInfoEnabled())
            log.info("temporary point has built,wait for next leader elect :" + path);
    }

    /**
     * 提供给外部调用，获取主从状态
     * @return
     */
    public boolean isLeader() {
        return isLeader;
    }

    /**
     * 显式关闭zkclient
     */
    public void close() {
        try {
            zkClient.unsubscribeAll();
            zkClient.close();
        } catch (Exception e) {
            log.warn("Close zkClient error:", e);
        }
        isLeader = false;
    }

    public static void main(String[] args) {
        // 模拟三个客户端
        for (int j = 0 ; j < 3 ; j++) {
            Thread thread = new Thread(new LeaderThread("10.1.61.101:2181", "/test","host" + j));
            thread.start();
        }
    }
}

/**
 * 模拟多个进程同时争抢锁
 */
@Slf4j
class LeaderThread implements Runnable {

    private String zkServers;
    private String path;
    private String host;

    public LeaderThread(String zkServers, String path, String host) {
        this.host = host;
        this.zkServers = zkServers;
        this.path = path;
    }

    @Override
    public void run() {
        Leader zk = new Leader(zkServers, path, host);
        int i = 0;
        // 防止线程退出
        while (true) {
            try {
                Thread.sleep(10000);
                i++;
                // 每2s模拟一次宕机
                if (i % 2 == 0) {
                    if (zk.isLeader()) {
                        zk.close();
                        log.info(host + "closed");
                    }
                }
                log.info("init finish current " + host +" status " + zk.isLeader());

            } catch (InterruptedException e) {

            }
        }
    }
}