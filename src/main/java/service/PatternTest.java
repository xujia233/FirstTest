package service;

import entity.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by xujia on 2019/5/20
 */
public class PatternTest {

    private Pattern pattern = Pattern.compile("^\\((.+?)$");

    @Test
    public void test01() {
//        System.out.println(14 % 4);
//        System.out.println(3 & 14);
        Date date= new Date();
        Long currentTime = date.getTime();
        date.setSeconds(0);
        Long CurrentMinBegin = date.getTime();
        System.out.println(currentTime);
        System.out.println(CurrentMinBegin);
    }

    @Test
    public void test02() {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.SECOND));

        //calendar.set(Calendar.SECOND, 0);


//        calendar.setTimeInMillis(new Date().getTime());
//        System.out.println(calendar.get(Calendar.MONTH));
    }

    @Test
    public void test03() {
//        int cpu = Runtime.getRuntime().availableProcessors();
//        System.out.println(cpu);
        User user = new User();
        if (user.getStr().contains("s")) {
            System.out.println("contain");
        }
        System.out.println("hhh");
    }

    @Test
    public void test04() {
        List<String> list = new ArrayList<>();
        list.forEach(str -> {
            if (null == str) {
                System.out.println("null");
            }
        });
    }
}
