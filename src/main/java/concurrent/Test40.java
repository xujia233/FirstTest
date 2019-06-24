package concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test40 {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            System.out.println(test());
        }
    }

    private static int test() throws InterruptedException {
        Map<String, Integer> map = new HashMap<String, Integer>();
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 8; i++) {
            pool.execute(new MyTask(map));
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);

        return map.get(MyTask.KEY);
    }
}

class MyTask implements Runnable {

    public static Object lock = new Object();

    public static final String KEY = "key";

    private Map<String, Integer> map;

    public MyTask(Map<String, Integer> map) {
        this.map = map;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            synchronized (lock) {
                this.addup();
            }
        }
    }

    private void addup() {
        if (!map.containsKey(KEY)) {
            map.put(KEY, 1);
        } else {
            map.put(KEY, map.get(KEY) + 1);
        }
    }

}  