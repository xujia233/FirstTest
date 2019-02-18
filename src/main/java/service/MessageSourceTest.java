package service;

import common.I18nContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Locale;

/**
 * 国际化测试类 context初始化时启动该类，读取properties文件，支持国际化
 * Created by xujia on 2019/2/15
 */
public class MessageSourceTest {

    @Test
    public void test01() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        MessageSource messageSource = (MessageSource) context.getBean("messageSource");
        String info1 = messageSource.getMessage("hello",null, Locale.getDefault());
        String info2 = messageSource.getMessage("hello",null, Locale.US);
        System.out.println("zh_CN info1 :" + info1);
        System.out.println("en_US info2 :" + info2);
    }

    /**
     * Local类用来获取国际化语言
     */
    @Test
    public void test02() {
        Locale locale = Locale.getDefault();
        System.out.println("country :" + locale.getCountry());
        System.out.println("language :" + locale.getLanguage());
        System.out.println(Locale.getDefault());
        System.out.println(Locale.US);
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new ThreadTest("en_US"));
        Thread thread2 = new Thread(new ThreadTest("en_CN"));
        Thread thread3 = new Thread(new ThreadTest("en_CA"));
        thread1.start();
        thread2.start();
        thread3.start();
    }

    /**
     * 多线程环境下，程序退出的条件为：所有非daemon线程都正常结束或者某个线程调用了System.exit()方法，导致进程强制退出。
     * 在eclipse下运行junit的类就调用了该方法，类比一下idea下应该也是，所以导致junit测试启动线程时，线程未调用完成就突然结束的情况发生。
     * 解决方案：1、改用main方法测试     2、依旧使用junit，但需配合使用sleep()方法
     */
    @Test
    public void test03() {
        Thread thread1 = new Thread(new ThreadTest("en_US"));
        Thread thread2 = new Thread(new ThreadTest("en_CN"));
        Thread thread3 = new Thread(new ThreadTest("en_CA"));
        thread1.start();
        thread2.start();
        thread3.start();
    }

}

/**
 * 语言设置线程
 */
class ThreadTest implements Runnable{

    private String language;

    public ThreadTest(String language) {
        this.language = language;
    }

    @Override
    public void run() {
        I18nContext.setLang(language);
        System.out.println(I18nContext.getLang());
    }
}
