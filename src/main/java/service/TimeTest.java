package service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by xujia on 2019/2/18
 */
@Slf4j
public class TimeTest {

    @Test
    public void test01 () throws Exception{

//        LocalDateTime localDateTime = LocalDateTime.parse(utcDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
//        localDateTime.atZone(TimeZone.getTimeZone("UTC").toZoneId());
//        return localDateTime.format(DateTimeFormatter.ofPattern(dateFormat));

        String time = "2019-02-18 15:13:26";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = format.parse(time);

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//        System.out.println(simpleDateFormat.format(date));
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), TimeZone.getTimeZone("UTC").toZoneId());
        System.out.println(        date.toInstant().toString()
        );
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));


//        LocalDateTime localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));


    }

    @Test
    public void test02 () throws Exception{
        String time2 = "2019-02-18 15:13:26";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = format.parse(time2);
        Object time = time2;
        LocalDateTime localDateTime = LocalDateTime.now();
        if (time instanceof Date) {
            localDateTime = LocalDateTime.ofInstant(((Date)time).toInstant(), ZoneId.systemDefault());
        } else if (time instanceof String) {
            localDateTime = LocalDateTime.parse((String)time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
    }

    @Test
    public void test03 () {
        System.out.println("原时间 " + new Date());

        TimeZone time = TimeZone.getTimeZone(ZoneId.systemDefault());
        System.out.println(time);

        TimeZone.setDefault(time);

        System.out.println("修改后时间 " + new Date());






//        SimpleDateFormat format = new SimpleDateFormat(
//                "yyyy-MM-dd HH:mm:ss");
//        Calendar cal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
//        format.setCalendar(cal);
//        return format.format(date);
    }

    @Test
    public void test04 () throws Exception{
        String s = "2019-02-19T05:49:07.911Z";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
//        Calendar calendar = Calendar.getInstance(new SimpleTimeZone(8, "CST"));
//        sdf.setCalendar(calendar);
        try {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(sdf.parse(s));
//            calendar.add(Calendar.HOUR, 16);
            System.out.println(sdf.parse(s));
        }
        catch (ParseException e) {
        }
    }

    @Test
    public void test05 () {
        Date date1 = new Date();
        System.out.println(date1);
        //System.setProperty("user.timezone","UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Date date = new Date();
        System.out.println(date);

    }

    @Test
    public void test06() {
        double date = 2;
        double hour = 60;
        double minute = 20;
        double interval = date + hour/60;
        System.out.println(interval);
    }

    @Test
    public void test07() {
        String str = "variable.code";
        if (str.startsWith("variable")) {
            str = str.substring(9);
            System.out.println(str);
        }
    }

    @Test
    public void test08 () {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)}");
        Matcher matcher = pattern.matcher("${ticket.variable.code}");
        if (matcher.find()) {
            System.out.println("yes");
        }
    }

    @Test
    public void test09() {
//        String str = "fk";
//        String str1 = "fk1";
//        log.info("参数 str:{},str1:{}",str, str1);
//        String uuid = "638696d6afd74c21a160241f087dbbfa";
//        if (uuid.matches("(\\w{8}(\\w{4}){3}\\w{12}?)")) {
//            System.out.println(true);
//
//        }
        List<String> strings = Lists.newArrayList("1","1","2");
        System.out.println(strings.stream().distinct().collect(Collectors.toList()));

    }
}
