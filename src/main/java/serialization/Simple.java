package serialization;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by xujia on 2020/1/2
 */
public class Simple implements Serializable {

    private static final long serialVersionUID = 7085013819438765925L;
    private String name;

    private int age;

    private Map<String, Integer> map;

    public Simple() {}

    public Simple(String name, int age, Map<String, Integer> map) {
        this.name = name;
        this.age = age;
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Map<String, Integer> getMap() {
        return map;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }
}
