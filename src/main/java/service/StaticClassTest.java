package service;

import lombok.Data;

/**
 * 静态编译测试类
 * Created by xujia on 2019/3/12
 */
@Data
public class StaticClassTest {

    private String id;
    private String name;
    private Integer age;
    public String address;

    public StaticClassTest(String id, Integer age) {
        this.id = id;
        this.age = age;
    }

    public StaticClassTest() {}

    @Deprecated
    public void say() {
        System.out.println("hello");
    }
}
