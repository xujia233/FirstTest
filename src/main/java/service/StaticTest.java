package service;

import jdk.internal.dynalink.beans.StaticClass;

/**
 * Created by xujia on 2019/4/21
 */
public class StaticTest {

    {
        System.out.println("fk");
    }
    static {
        System.out.println("static");
    }
    public static void main(String[] args) {
        StaticTest staticClass = new StaticTest();
    }
}
