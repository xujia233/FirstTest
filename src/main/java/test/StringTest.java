package test;

import com.google.common.collect.Lists;
import entity.User;
import org.junit.Test;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xujia on 2019/7/1
 */
public class StringTest {

//    public static void main(String[] args) {
//        while (true) {
//            System.out.println("请输入一个字符串:");
//            Scanner scanner = new Scanner(System.in);
//            String str = scanner.nextLine();
//            if (str.equalsIgnoreCase("end")) {
//                System.out.println("结束");
//                break;
//            } else {
//                System.out.println("输入字符串为:" + str);
//            }
//        }
//
//    }

    @Test
    public void test12() {
        String url = "hello%sworld%sfuck%s";
        url = String.format(url, "1", "2", null);
        System.out.println(url);
    }

    @Test
    public void test01() {
        StringBuilder builder = new StringBuilder();
        builder.append("表单1").append("未开启").append("\n");
        builder.append("表单2").append("未开启").append("\r\n");
        builder.append("表单3");
        System.out.println(builder.toString());
//        List<String> list = null;
//        list.forEach(s -> System.out.println(s));
//        try {
//            list.forEach(s -> System.out.println(s));
//        } catch (Exception e) {
//
//        }
//        System.out.println("11");
//        List<String> list = Lists.newArrayList("1","2","3","4","5");
//        System.out.println(list.subList(0,6));
        ChangeTypeEnum changeTypeEnum = ChangeTypeEnum.valueOf("GROUP");
        System.out.println();
    }

    @Test
    public void test07() {
        int i = 9;
        do {
            if (i > 5) {
                System.out.println(i);
            }
            if (i == 5) {
                continue;
            }

            i--;

        } while (i > 0);
    }

    @Test
    public void test02() {
        AtomicInteger atomicInteger = new AtomicInteger();
        for (int i = 0; i <= 3; i++) {
           System.out.println(atomicInteger.getAndIncrement());
        }

    }

    private int a = 0;

    public void add(int b) {
        int t = a;
        int c = a + b;
        compareAndSet(a, t, c);
    }

    private void compareAndSet(int v, int e, int n) {
        if (v == e) {
            v = n;
        }
    }

    @Test
    public void test03() {
        // ${ticket.}
        String str = "${ticket.code}${ticket.ded}";
        Pattern pattern = Pattern.compile("\\$\\{(.+?)}");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    @Test
    public void test04() {
        String str = "654";
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    // 当调用该方法时，虚拟机会创建一个栈帧用来存放该方法所需的基本数据信息
    public static void main(String[] args) {
        int a = 1; // 新建一个局部变量a，同时在当前虚拟机栈中为其分配内存，值为1
        User user = new User(); // 在当前堆空间中新建一个User对象实例，同时在栈中新建一个user对象的引用，用于指向user对象
        user.setName("张三"); // 对所引用的对象进行设值
        System.out.println(a + "," + user.getName());
        change(a, user); // java中只有值传递，只是针对基本类型来说其值就是其本身，针对对象来说，其值便是引用地址
        System.out.println(a + "," + user.getName()); // 根据值传递，a和user引用本身都未改变，虽然user引用本身未改变，但是其指向的user对象却被改变了，因此最终展现的值也与之前不同
    } // 方法调用结束，开始出栈，即变量a和引用user从栈中弹出，释放内存，但是user所指向的对象仍然存在，等待垃圾回收机制对其进行回收。每一个方法从开始调用到结束调用，都对应着栈帧从入栈到出栈的过程

    public static void change(int a, User user) {
        a = 2;
        user.setName("李四");
    }

    @Test
    public void test121() {
        //
        int a = 29;
        int b = 3;
        long mod = Math.floorMod(a, b);
        System.out.println(mod);
    }

}
