package service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xujia on 2019/1/25
 */
@Slf4j
public class PathTestService {

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test01() {
        // 返回的为当前用户工作目录，即在哪个地方启动的java线程，输出：/Users/xujia/Downloads/mydocument/idea-projects/test01
        System.out.println(System.getProperty("user.dir"));
        // 获取编译后的路径，输出：/Users/xujia/Downloads/mydocument/idea-projects/test01/target/classes/
        System.out.println(PathTestService.class.getClassLoader().getResource("").getPath());
    }

    @Test
    public void test02() {
        // 该方法内部实现大小写转换，比较时不区分大小写
        String s1 = "ABC";
        System.out.println(s1.equalsIgnoreCase("abc"));
        try {
            System.out.println(InetAddress.getLocalHost().toString());
        } catch (Exception e) {
            log.error("error");
        }
    }

    @Test
    public void test03() {
        // 测试break跳出指定循环层数，不指定for循环名称break只会跳出当前循环
        loop : for (int i = 0 ; i < 3 ; i++) {
            System.out.println("第一次循环 ："+i);
            for (int j = 1 ; j < 3 ; j++) {
                System.out.println("第二层循环 ："+j);
                if (j == 2) {
                    System.out.println("循环终止，此时j="+j);
                    break loop;
                }
            }
        }
    }

    @Test
    public void test04() {
        List<String> p = Lists.newArrayList();
        List<String> list = new ArrayList<>();
        list.add("s1");
        list.add("s2");
        list.add("s3");
        String[] strings = list.toArray(new String[list.size()]);
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(strings[0] + "," + strings[1] + "," + strings[2]);
            String s = mapper.writeValueAsString(strings);
            System.out.println(s);
            String[] strings1 = s.split(",");
            System.out.println(strings1[0] + "," + strings1[1] + "," + strings1[2]);
        } catch (Exception e) {

        }
    }

    /**
     * map与json互转、str与map互转
     */
    @Test
    public void test05() {

        Map<String, String> map = Maps.newHashMap();
        map.put("key01","value01");
        map.put("key02","value02");
        map.put("key03","value03");
        try {
            System.out.println("map :" + map);
            // 利用jackson进行互转
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(map);
            System.out.println("map to json 1 :" + json);
            // 利用fastJson进行互转
            Object o = JSONObject.toJSON(map);
            System.out.println("map to json 2 :" + o.toString());
            System.out.println("json to map 1 :" + mapper.readValue(json, Map.class));
            System.out.println("json to map 2 :" + JSON.parseObject(json, Map.class));
            String mapToStr = Joiner.on(",").withKeyValueSeparator(":").join(map);
            System.out.println("map to str :" + mapToStr);
            // 利用Splitter内部类的引用，得到分割器，将字符串解析为map
            Splitter.MapSplitter ms = Splitter.on(",").withKeyValueSeparator(":");
            System.out.println("str to map :" + ms.split(mapToStr));
        } catch (Exception e) {

        }
    }

    @Test
    public void test06() {
        int a = 10;
        String s1 = null;
        Preconditions.checkNotNull(s1, "s1 is null");
        //Preconditions.checkArgument(a > 100, "a must be more than 100");
        String s2 = "s1";
        if (s2 != null)
            System.out.println(s2.split(",")[0]);

    }



}
