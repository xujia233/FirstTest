package service;

import org.junit.Test;
import proxy.People;

import java.util.ServiceLoader;

/**
 * Created by xujia on 2019/4/15
 */
public class SPITest {

    @Test
    public void test01() {
        ServiceLoader<People> serviceLoader = ServiceLoader.load(People.class);
        serviceLoader.forEach(People::say);
    }
}
