package proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xujia on 2019/8/8
 */
public class TestList {

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("key", new ArrayList<>());
        map = (Map<String, Object>) map.put("key", new ArrayList<>());
        System.out.println(map);
    }

}
