package service;

import org.junit.Test;

/**
 * 测试静态分派（重载） / 以及重载的最佳选择，编译器所选择的重载版本只能说是最合适的那一个，而不是说唯一的，这主要是因为有些字面量不需要
 * 显示的定义，也就是说这个字面量没有静态类型，编译器只能根据语义自行分析，本文会举个例子
 * Created by xujia on 2019/11/26
 */
public class StaticDispatch {

    public static void main(String[] args) {
        StaticDispatch staticDispatch = new StaticDispatch();
        Human human = new Man();
        staticDispatch.print(human);
        human = new Woman();
        staticDispatch.print(human);
        // 编译器在重载时选择哪个版本是根据参数的静态类型来作为判定依据的，而不是实际类型，静态类型只在使用时会发生变化，变量本身的
        // 静态类型并不会变化，因此在编译器可知，而
        // 实际类型变化的结果只有在运行期才可确定，编译器在编译程序的时候并不知道一个参数的实际类型是什么
        staticDispatch.print((Woman) human);
    }

    @Test
    public void test() {
        StaticDispatch staticDispatch = new StaticDispatch();
        // 这里就是所说根据字面量来分析并选择最适合的重载版本，当注释char c的重载方法时
        staticDispatch.sayHello('c');
    }

//    public void sayHello(char c) {
//        System.out.println("hello char");
//    }
//
//    public void sayHello(int c) {
//        System.out.println("hello int");
//    }
//
//    public void sayHello(long c) {
//        System.out.println("hello long");
//    }

    // 甚至可以自动装箱
    public void sayHello(Character c) {
        System.out.println("hello character");
    }

    static class Human {}

    static class Man extends Human {}

    static class Woman extends Human {}

    public void print(Human human) {
        System.out.println("hello human");
    }

    public void print(Man human) {
        System.out.println("hello man");
    }

    public void print(Woman human) {
        System.out.println("hello woman");
    }
}
