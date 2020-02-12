package common;

import org.junit.Test;

/**
 * Created by xujia on 2019/11/22
 */
public class ClassLoadTest {

    public static void main(String[] args) {
        // 只会初始化定义该字段的类，不会初始化其子类
        System.out.println(ChildClass.CONSTANTS);
    }

    @Test
    public void test01() {
        // 应用程序加载器，负责加载用户类路径上所指定的类库
        System.out.println(ClassLoader.getSystemClassLoader());
    }
}

class ParentClass {

    static {
        System.out.println("parent static init");
    }

    // 被final修饰的静态字段不会触发当前类的初始化
    // public static String CONSTANTS = "constants";

    // 如果是常量，即被final static修饰的字段不会触发类的初始化，因为在编译阶段通过常量传播优化已经存入调用类的常量池中，本质上并没有直接引用到定义常量的类，因此不会触发该类的初始化
    public static final String CONSTANTS = "constants";
}

class ChildClass extends ParentClass {

    static {
        System.out.println("child static init");
    }

}
