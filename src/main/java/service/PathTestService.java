package service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import entity.User;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by xujia on 2019/1/25
 */
@Slf4j
public class PathTestService {

    private final Random random = new Random();

    @Test
    public void tt() {
        String t1 = "1000";
        int t2 = 10;
        double d1 = Double.valueOf(t1);
        double d2 = Double.valueOf(t2);
        System.out.println(d1 > d2);
    }

    @Test
    public void test00() {
        // 随机获取一个 [0,5) 的数字
//        System.out.println(random.nextInt(5));
//        System.out.println(random.nextInt(5));
//        System.out.println(random.nextInt(5));
//        System.out.println(random.nextInt(5));
//        System.out.println(random.nextInt(5));
//        System.out.println(random.nextInt(5));
//        System.out.println(random.nextInt(5));
//        System.out.println(random.nextInt(5));
        System.out.println(String.format("temporary point has built,wait for next leader elect。[path=%s]", "/xujia/uyun"));
    }

    @Test
    public void test3333() {
        int n = 16 - (16 >>> 2);
        System.out.print(n);
    }

    @Test
    public void test01() throws Exception {
        // 返回的为当前用户工作目录，即在哪个地方启动的java线程，输出：/Users/xujia/Downloads/mydocument/idea-projects/test01
        System.out.println(System.getProperty("user.dir"));
        // 获取编译后的路径，输出：/Users/xujia/Downloads/mydocument/idea-projects/test01/target/classes/
        System.out.println(PathTestService.class.getClassLoader().getResource("").getPath());
        System.out.println(PathTestService.class.getClassLoader().getResource("").getFile());
        System.out.println(InetAddress.getLocalHost().toString());
    }

    @Test
    public void test09() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);


        CountDownLatch countDownLatch = new CountDownLatch(1);
        executorService.execute(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("线程执行完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        });
        try {
            if (countDownLatch.await(2, TimeUnit.SECONDS)) {
                System.out.println("任务执行成功");
            } else {
                System.out.println("执行超时");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//        // 采用Future
//        Future<Boolean> future = executorService.submit(() -> {
//            Thread.sleep(3000);
//            System.out.println("线程执行完成");
//            return true;
//        });
//
//        try {
//            if (future.get(2, TimeUnit.SECONDS)) {
//                System.out.println("任务执行成功");
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            System.out.println("执行超时");
//            future.cancel(true);
//        }

        //executorService.shutdown();
//        try {
//            if (executorService.awaitTermination(2, TimeUnit.SECONDS)) {
//                System.out.println("主线程开始执行");
//            } else {
//                System.out.println("执行超时");
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            executorService.shutdown();
//        }

    }

    @Test
    public void test02() {
        // 该方法内部实现大小写转换，比较时不区分大小写
        String s1 = "ABC";
        System.out.println(s1.equalsIgnoreCase("abc"));
        try {
            System.out.println(InetAddress.getLocalHost().toString());
        } catch (Exception e) {
            log.error("error");
        }
    }

    @Test
    public void test03() {
        // 测试break跳出指定循环层数，不指定for循环名称break只会跳出当前循环
        for (int i = 1; i < 5; i++) {
            System.out.println("第一层循环 ：" + i);
            for (int j = 1; j < 5; j++) {
                System.out.println("第二层循环 ：" + j);
                if (j == 2) {
                    System.out.println("循环终止，此时j=" + j);
                    break;
                }
            }
        }
        System.out.println(Locale.CHINA.toString());
        System.out.println(Locale.US.toString());
    }

    @Test
    public void test04() {
        List<String> p = Lists.newArrayList();
        List<String> list = new ArrayList<>();
        list.add("s1");
        list.add("s2");
        list.add("s3");
        String[] strings = list.toArray(new String[list.size()]);
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(strings[0] + "," + strings[1] + "," + strings[2]);
            String s = mapper.writeValueAsString(strings);
            System.out.println(s);
            String[] strings1 = s.split(",");
            System.out.println(strings1[0] + "," + strings1[1] + "," + strings1[2]);
        } catch (Exception e) {

        }
    }

    @Test
    public void asdas() {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, String>> list = (List<Map<String, String>>) map.get("hi");
        System.out.println(list);
    }

    /**
     * map与json互转、str与map互转
     */
    @Test
    public void test05() {

        Map<String, String> map = Maps.newHashMap();
        map.put("key01", "value01");
        map.put("key02", "value02");
        map.put("key03", "value03");
        try {
            System.out.println("map :" + map);
            // 利用jackson进行互转
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(map);
            System.out.println("map to json 1 :" + json);
            // 利用fastJson进行互转
            Object o = JSONObject.toJSON(map);
            System.out.println("map to json 2 :" + o.toString());
            System.out.println("json to map 1 :" + mapper.readValue(json, Map.class));
            System.out.println("json to map 2 :" + JSON.parseObject(json, Map.class));
            String mapToStr = Joiner.on(",").withKeyValueSeparator(":").join(map);
            System.out.println("map to str :" + mapToStr);
            // 利用Splitter内部类的引用，得到分割器，将字符串解析为map
            Splitter.MapSplitter ms = Splitter.on(",").withKeyValueSeparator(":");
            System.out.println("str to map :" + ms.split(mapToStr));
        } catch (Exception e) {

        }
    }

    /**
     * 断言测试
     */
    @Test
    public void test06() {
        int a = 10;
        String s1 = null;
        Preconditions.checkNotNull(s1, "s1 is null");
        //Preconditions.checkArgument(a > 100, "a must be more than 100");
        String s2 = "s1";
        if (s2 != null)
            System.out.println(s2.split(",")[0]);

    }

    @Test
    public void test07() {
        List<String> list1 = Lists.newArrayList("1", "2", "3", "1", "2");
        List<String> list2 = Lists.newArrayList("8", "8");
        List<String> nn = new ArrayList<>();
        List<String> hh = nn.stream().filter(s -> s.equals("2")).collect(Collectors.toList());
        System.out.println(hh);
        list1.remove(0);
        list1.addAll(0, list2);
        System.out.println(list1);
        System.out.println(File.separator);
    }

    @Test
    public void test08() {
        Set<String> all = new HashSet<>();
        // 以后这个addAll方法要注意！
        //all.addAll(null);
        List<String> test = null;
        test.forEach(s -> System.out.println(s));


//        for (String s : test) {
//            System.out.println(s);
//        }


        User user = new User("1", "1");
        User user1 = new User("1", "1");
        User user2 = new User("2", "1");
        User user3 = new User("3", "1");
        User user4 = new User("3", "1");
        User user5 = new User("4", "1");
        List<User> users = Lists.newArrayList(user, user1, user2, user3, user4, user5);
        //System.out.println(users);
        List<User> hhhh = new ArrayList<>();
        Set<String> ll = hhhh.stream().map(User::getId).collect(Collectors.toSet());
        all.addAll(ll);

        Set<String> ids = users.stream().map(User::getId).collect(Collectors.toSet());
        System.out.println(ids);
        List<User> h = null;
        //ss(h);
        //System.out.println(ids);
    }

    private void ss(List<User> users) {
        users.forEach(user -> System.out.println(user.getId()));
    }

    @Test
    public void test12() {
        Set<Integer> set1 = new HashSet<>();
        set1.add(1);
        set1.add(2);
        set1.add(3);
        set1.add(4);
        if (set1.add(5)) {
            System.out.println("add true");
        }
        if (!set1.add(5)) {
            System.out.println("add false");
        }


        Set<Integer> set2 = new HashSet<>();
        set2.add(1);
        set2.add(3);
        set2.add(4);
        set2.add(5);
        set2.add(6);
        set2.add(7);
        // guava的差集是找出set2有而set1没有的元素，并不是真正意义上的差集
        Sets.SetView setView = Sets.difference(set2, set1);



        Sets.SetView setView1 = Sets.intersection(set1, set2);
        System.out.println(setView);
        System.out.println(setView1);
    }

    @Test
    public void test() {
        Set<User> set1 = new HashSet<>();
        set1.add(new User("1", "1"));
        set1.add(new User("2", "2"));
        set1.add(new User("3", "4"));
        set1.add(new User("4", "4"));

        Set<User> set2 = new HashSet<>();
        set2.add(new User("1", "1"));
        set2.add(new User("3", "3"));
        set2.add(new User("4", "4"));
        set2.add(new User("5", "5"));

        Sets.SetView setView = Sets.difference(set2, set1);



        Sets.SetView setView1 = Sets.intersection(set1, set2);
        System.out.println(setView);
        System.out.println(setView1.size());
        System.out.println(setView1);
    }

    @Test
    public void test2() {
        String[] array = new String[]{"1", "2", "3"};
        // asList本质运用的是适配器模式，其内部数据结构仍是数组
        List<String> list = Arrays.asList(array);
        List<String> list2 = new ArrayList<>();
        System.out.println(list);
        // InputStreamReader reader = new InputStreamReader();
    }

    @Test
    public void test031() {
        //InputStreamReader





        HashMap<String, List<String>> map = new HashMap<>();
        List<User> set1 = new ArrayList<>();
        set1.add(new User("1", "1"));
        set1.add(new User("2", "2"));
        set1.add(new User("3", "4"));
        set1.add(new User("4", "4"));
        set1.add(new User("1", "4"));
        set1.add(new User("2", "4"));
        set1.add(new User("3", "4"));
        set1.add(new User("4", "4"));

        set1.forEach(user -> {
            List<String> list = map.computeIfAbsent(user.getId(), v -> new ArrayList<>());
            list.add(user.getName());
        });

        int i = 1;
        i=i+2;



    }


    @Test
    public void tt1() {
        String[] array = new String[]{"1","2","3"};
        List list = Arrays.asList(array);
        System.out.println(list.get(0));
        array[0] = "9";
        System.out.println(list.get(0));
    }

    @Test
    public void regexText() {
        String kw = "关键词";
        String regex = ".*"+ kw + ".*";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher("哈哈哈哈我是键词");
//        System.out.println(matcher.find());
        System.out.println(Pattern.matches(regex, "关键词hhh我是"));

    }

//    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
//        // 表示延迟3s执行，只会执行一次
//        // executorService.schedule(() -> System.out.println("delay 3 seconds"), 3, TimeUnit.SECONDS);
//        // 表示延迟1s启动线程，然后每3s执行一次，定时任务
//        executorService.scheduleAtFixedRate(() -> System.out.println("Delay 1s and execute every 3s"), 1, 3, TimeUnit.SECONDS);
//    }

    @Test
    public void priorityQueue() {
        PriorityQueue<DelayTask> queue = new PriorityQueue<>();
//        queue.add(new DelayTask(4000, "task4"));
//        queue.add(new DelayTask(1000, "task1"));
//        queue.add(new DelayTask(2000, "task2"));
//        queue.add(new DelayTask(3000, "task3"));
        queue.add(new DelayTask(transferDelayTime(4000), "task4"));
        queue.add(new DelayTask(transferDelayTime(1000), "task1"));
        queue.add(new DelayTask(transferDelayTime(2000), "task2"));
        queue.add(new DelayTask(transferDelayTime(3000), "task3"));
        while (queue.size() > 0) {
            System.out.println(queue.poll());
        }
    }

    /**
     * 延迟队列例子
     * @param args
     */
    public static void main(String[] args) {
        DelayQueue<DelayTask> queue = new DelayQueue<>();

        initConsumer(queue);
        try {
            // 模拟当队列中没数据时 延迟队列的阻塞情况
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 初始化提供者

        queue.add(new DelayTask(transferDelayTime(4000), "task4"));
        queue.add(new DelayTask(transferDelayTime(1000), "task1"));
        queue.add(new DelayTask(transferDelayTime(2000), "task2"));
        queue.add(new DelayTask(transferDelayTime(3000), "task3"));
    }

    private static long transferDelayTime(long time) {
        return TimeUnit.MILLISECONDS.toNanos(time) + System.nanoTime();
    }

    /**
     * 初始化消费者线程
     */
    private static void initConsumer(DelayQueue queue) {
        Runnable task = () -> {
            while (true) {
                try {
                    System.out.println("尝试获取延迟队列中的任务。" + LocalDateTime.now());
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread consumer = new Thread(task);
        consumer.start();
    }


    /**
     * 延迟任务实例
     */
    static class DelayTask implements Delayed {

        /**
         * 延迟时间，毫秒
         */
        private long delayTime;
        /**
         * 业务信息，用于区分例子
         */
        private String msg;

        public DelayTask(long delayTime, String msg) {
            this.delayTime = delayTime;
            this.msg = msg;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(delayTime - System.nanoTime(), TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (null == o)
                return 0;
            if (o instanceof DelayTask) {
                DelayTask delayTask = (DelayTask) o;
                // 这里千万不能强转！！！否则超出了int的数值范围，经过测试是会变成负值，但是如果int*int然后超过最大值范围会截取低32位，高位会直接抛弃
                long diff = this.delayTime - delayTask.delayTime;
                return diff > 0 ? 1 : diff < 0 ? -1 : 0;
            }
            long diff = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return diff > 0 ? 1 : diff < 0 ? -1 : 0;
        }

        @Override
        public String toString() {
            return "DelayTask{" +
                    "delayTime=" + delayTime +
                    ", msg='" + msg + '\'' +
                    '}';
        }

//        @Override
//        public int compareTo(Object o) {
//            DelayTask delayTask = (DelayTask) o;
//            return (int) (this.delayTime - delayTask.delayTime);
//        }
    }


    @Test
    public void test112() {
//        TimeUnit unit = TimeUnit.MILLISECONDS;
//        long currentTime = Instant.now().toEpochMilli();
//        System.out.println(currentTime);
//        System.out.println(unit.convert(1000 - currentTime, TimeUnit.MILLISECONDS));
//        // 返回毫微妙
//        System.out.println(System.nanoTime());

        long time1 = transferDelayTime(1000);
        long time2 = transferDelayTime(4000);
        System.out.println(time1);
        System.out.println(time2);
        long diff = time2 - time1;
        System.out.println("diff:" + diff);
        System.out.println("int diff:" + (int) diff);

        System.out.println(0 >>> 1);
    }

    @Test
    public void test13() {
        // 主要由于基本类型无法被泛型化且Arrays.asList支持传入的参数为可变长泛型，因此它把整个数组当成了一个泛型对象，因此集合中最终只有一个元素
        int[] arr = new int[]{1, 2, 3};
        List list = Arrays.asList(arr);
        System.out.println(list.size());
    }

    @Test
    public void test14() {
        Integer i = 127;
        Integer j = 127;
        System.out.println(i == j);
        Integer k = 128;
        Integer o = 128;
        System.out.println(k == o);
    }

    @Test
    public void test15() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        System.out.println(atomicInteger.incrementAndGet());
        System.out.println(atomicInteger.addAndGet(3));
    }
}


