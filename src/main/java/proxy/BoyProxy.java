package proxy;

import impl.Boy;
import org.junit.Test;

import java.lang.reflect.Proxy;

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
}
