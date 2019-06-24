package service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Scanner;

/**
 * Created by xujia on 2019/3/12
 */
@Slf4j
public class ReflectTest {

    public static void main(String[] args) {
        System.out.println("请输入类的全路径，这里为了展现这种思想请填写指定的类型");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        try {
            // 加载这个类
            Class c = Class.forName(s);
            // 获得这个类的实例化对象
            Object obj = c.newInstance();
            // 强制类型转换
            StaticClassTest staticClassTest = (StaticClassTest) obj;
            staticClassTest.say();
        } catch (Exception e) {

        }
    }

    @Test
    public void test01() {
        try {
            // Class clz = Class.forName("service.StaticClassTest");
            // Class clz = StaticClassTest.class;
//            StaticClassTest staticClassTest = new StaticClassTest();
//            Class clz = staticClassTest.getClass();
            Class clz = StaticClassTest.class;
            Constructor constructor = clz.getConstructor(String.class, Integer.class);
            StaticClassTest staticClassTest = (StaticClassTest) constructor.newInstance("小明", 25);

        } catch (Exception e) {

        }
    }

    @Test
    public void test02() {
        try {
            Class clz = StaticClassTest.class;
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                System.out.println(field);
            }
        } catch (Exception e) {
            log.error("reflect error");
        }
    }

    @SuppressWarnings("deprecation")
    public void test03 () {
        StaticClassTest staticClassTest = new StaticClassTest();
        staticClassTest.say();
    }



}
