package service;

import com.google.common.collect.Lists;
import entity.User;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 集合总结测试类
 * Created by xujia on 2019/2/24
 */
public class CollectionTest {

    @Test
    public void test01 () {
        // Set元素为基本类型时，其不可重复特性
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        set.add("c");
        System.out.println(set);
        set.add("a");
        set.add("d");
        System.out.println(set);
    }

    @Test
    public void test02 () {
        // Set元素为对象时，通过重写对象的equals和hashcode方法来实现不可重复特性
        Set<User> users = new HashSet<>();
        users.add(new User("1","张三"));
        users.add(new User("1","张三"));
        System.out.println(users);
        users.add(new User("2","张三"));
        System.out.println(users);
    }

    @Test
    public void test03 () {
        Map<String, String> map = new HashMap<>();
        map.put("1", null);
        map.put(null, "null");
        map.put("2", null);
        System.out.println(map.get(null) + "," + map.get(1) + "," + map.get(2));
        // 使map变为线程安全
        Map<String, String> map1 = Collections.synchronizedMap(map);
    }

    @Test
    public void test04 () {
        // List集合去重demo
        List<String> list = Lists.newArrayList("a","a","b","c");
        list = list.stream().distinct().collect(Collectors.toList());
        System.out.println(list);
//        List<String> newList = new ArrayList<>();
//        newList.addAll(new HashSet<>(list));
//        System.out.println(newList);



        System.out.println(new HashSet<>(list));
    }

    @Test
    public void test05 () {
        User user1 = new User("a", "a1");
        User user2 = new User("a", "a1");
        User user3 = new User("b", "b1");
        User user4 = new User("c", "c1");
        List<User> users = Lists.newArrayList(user1, user2, user3, user4);
        System.out.println("before :" + users);
        users = users.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(
                                () -> new TreeSet<>(Comparator.comparing(User::getId))
                        ), ArrayList::new
                )
        );
        System.out.println("after :" + users);
    }


}
