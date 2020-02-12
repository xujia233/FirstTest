package service;

import org.junit.Test;

/**
 * 动态分派，即重写，即虚拟机根据参数的实际类型来判断调用哪一个版本的方法
 * Created by xujia on 2019/11/26
 */
public class DynamicDispatch {

    @Test
    public void test() {
        Human human = new Human();
        human.sayHello();
        human = new Man();
        human.sayHello();
        human = new Woman();
        human.sayHello();
    }

    // 测试值传递和引用传递
    // 基本类型为值传递，对象则是按引用传递，基本类型透传时传递的其实是值参数的副本，而对象传递时是引用对象的地址值，其实统称为值传递
    @Test
    public void test01() {
        int num = 50;
        change(num);
        System.out.println(num);
        String s = "hello";
        change(s);
        System.out.println(s);
    }

    public void change(int value) {
        value = 100;
    }

    public void change(String value) {
        value = "str";
    }

    class Human {
        void sayHello() {
            System.out.println("human say hello");
        }
    }

    class Man extends Human {
        void sayHello() {
            System.out.println("man say hello");
        }
    }

    class Woman extends Human {
        void sayHello() {
            System.out.println("woman say hello");
        }
    }
}
