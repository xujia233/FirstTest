package collection.sort;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by xujia on 2019/12/10
 */
public class SortTest {

    public static void main(String[] args) {
        LinkedList<Object> list = new LinkedList<>();
        Task task = new Task("1", "task1", new Date());
        System.out.println(task.getTime().getTime());

        Approval approval = new Approval("3", "approval1", System.currentTimeMillis() + 10);
        System.out.println(approval.getTime());
        Fuck fuck = new Fuck("2", "fuck1", new Date());
        System.out.println(fuck.getTime().getTime());

        list.add(task);
        list.add(fuck);
        list.add(approval);
        list.sort(Collections.reverseOrder());
        //Collections.reverse(list);
        System.out.println(list);
    }

    @Test
    public void test01() {
        long date = System.currentTimeMillis();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault());
        String time = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(time);
    }

    @Test
    public void test02() {
        String str = "88f546916bc841b58d954a984e09d865:autoResult";
        str = str.substring(0, 32);
        System.out.println(str);
    }

}
