package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by xujia on 2019/4/3
 */
public class JsonUtilTest {

    @Test
    public void test01 () {
        List<String> str = Lists.newArrayList("1","@");
        String s = JSONObject.toJSONString(str);
        System.out.println(JSONObject.parseObject(s, List.class));

        //System.out.println(JSONObject.toJSONString(str));
        long time0 = System.currentTimeMillis();
        long time1 = new Date().getTime();
        long time2 = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long time3 = Calendar.getInstance().getTimeInMillis();
        System.out.println(time0);
        System.out.println(time1);
        System.out.println(time2);
        System.out.println(time3);
    }

    @Test
    public void test02 () throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        List<String> str = Lists.newArrayList("1","@");
        System.out.println(mapper.writeValueAsString(str));
    }
}
