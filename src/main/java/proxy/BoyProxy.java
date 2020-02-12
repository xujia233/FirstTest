package proxy;

import com.google.common.collect.Lists;
import entity.User;
import impl.Boy;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 静态代理 需与代理类实现相同的接口
 * 优点：可以做到在符合开闭原则时对目标对象进行功能扩展
 * 缺点：1、需要为每个服务都创建一个代理类，工作量太大
 *      2、同时一旦目标接口发生改变，代理类也得相应修改
 * Created by xujia on 2019/4/17
 */
public class BoyProxy implements People {

    private Boy boy;

    @Override
    public void say() {
        boy = new Boy();
        System.out.println("before say");
        boy.say();
    }

    @Test
    public void test0() {
        // 静态代理
        People people = new BoyProxy();
        people.say();
    }

    @Test
    public void test () {
        // 下面演示jdk动态代理
        People dynamicProxy = (People) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{People.class}, ((proxy, method, args) -> {
            People boy = new Boy();
            System.out.println("before say");
            Object o = method.invoke(boy, args);
            System.out.println("after say");
            return o;
        }));
        dynamicProxy.say();
    }

    @Test
    public void test02() {
        // 下面演示Cjlib动态代理
        People boy = new Boy();
        People proxy = (Boy) new CglibProxy().getInstance(boy);
        proxy.say();
    }

    @Test
    public void test03() {
        Map<String, Object> map = new HashMap<>();
        List<User> users = (List<User>) map.getOrDefault("list", null);
        System.out.println(users);
    }

    @Test
    public void test04() {
        List<String> userIds = Lists.newArrayList("1", "2", "3", "4");
        userIds = filter(userIds);
        System.out.println(userIds);
    }

    @Test
    public void test05() {
        User user = new User();
        System.out.println(user.getCan());
    }

    private List<String> filter(List<String> userId) {
        userId.removeIf(s -> "1".equals(s));
        return userId;
    }

    /**
     * 测试强转
     */
    @Test
    public void test06() {
        Map<String, Object> map = new HashMap<>();
        map.put("key", new ArrayList<>());
        map = (Map<String, Object>) map.put("key", new ArrayList<>());
        System.out.println(map);
    }

    @Test
    public void test07() {
        Map<String, Object> map = new HashMap<>();

        List<String> list = new ArrayList<>();
        TestList testList = new TestList();
        //map = (Map<String, Object>) testList.transfer(list);
        System.out.println(map);
    }

    @Test
    public void test08() {
        String t = null;
        List<String> s = Lists.newArrayList(t);
        if (!CollectionUtils.isEmpty(s))
            System.out.println(s);
    }

    @Test
    public void test09() {
        String a = "Test";
        String b = "Test";
        a = "b";
        "Test".toUpperCase();
        System.out.println("Test".toUpperCase() + "," + a + "," + b);
    }

}
