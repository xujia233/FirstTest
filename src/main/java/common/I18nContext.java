package common;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程语言，ThreadLocal实现
 * Created by xujia on 2019/2/15
 */
public class I18nContext {

    private static ThreadLocal<Map<String, String>> threadLocal = ThreadLocal.withInitial(HashMap::new);

    public static void setLang(String language) {
        Map<String, String> map = new HashMap<>();
        map.put("i18n.test", language);
        threadLocal.set(map);
    }

    public static String getLang() {
        return threadLocal.get().get("i18n.test");
    }

}
