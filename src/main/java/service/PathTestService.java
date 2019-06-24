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
import java.util.*;
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
        User user = new User("1", "1");
        User user1 = new User("1", "1");
        User user2 = new User("2", "1");
        User user3 = new User("3", "1");
        User user4 = new User("3", "1");
        User user5 = new User("4", "1");
        List<User> users = Lists.newArrayList(user, user1, user2, user3, user4, user5);
        Set<String> ids = users.stream().map(User::getId).collect(Collectors.toSet());

        List<User> h = null;
        ss(h);
        System.out.println(ids);
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

}
