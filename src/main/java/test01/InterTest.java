package test01;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xujia on 2019/8/19
 */
public class InterTest implements Inter01,Inter02 {
    public void say() {

    }

    @Test
    public void test0() {
        AtomicInteger i = new AtomicInteger();
        System.out.println(i.incrementAndGet());
        System.out.println(i.incrementAndGet());
    }

}
