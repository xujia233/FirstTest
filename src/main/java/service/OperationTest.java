package service;

import org.junit.Test;

/**
 * 位运算测试类 >> >>> <<
 * <<= 为复合运算符，a <<= 2 等价于 a = a << 2    <<=与+=类似，均不会改变数据类型
 * 负数的二进制表示是以原码的补码形式展现
 *
 *
 * 书上原文：
 * 原码表示法规定：用符号位和数值表示带符号数，正数的符号位用“0”表示，负数的符号位用“1”表示，数值部分用二进制形式表示。
 * 反码表示法规定：正数的反码与原码相同，负数的反码为对该数的原码除符号位外各位取反。
 * 补码表示法规定：正数的补码与原码相同，负数的补码为对该数的原码除符号位外各位取反，然后在最后一位加1。
 * Created by xujia on 2019/2/26
 */
public class OperationTest {

    @Test
    public void test01 () {
        int t = 4;
        System.out.println(Integer.toBinaryString(t)); // 100
//        t = t << 1;
//        System.out.println(Integer.toBinaryString(t)); // 1000
        // 右移1位
        t = t >> 1;
        System.out.println(Integer.toBinaryString(t)); // 100
        // 无符号右移1位，正数的>>>与>>得出的结果是一样的
        t = t >>> 1;
        System.out.println(Integer.toBinaryString(t)); // 100

        int i = -1;
        System.out.println(Integer.toBinaryString(i));
        i = i << 1;
        System.out.println(Integer.toBinaryString(i));
        i = i >>> 1;
        System.out.println(Integer.toBinaryString(i));
    }

    @Test
    public void test02 () {
        int i = 4;
        System.out.println(Integer.toBinaryString(i));
        i = i >>> 1;
        System.out.println(Integer.toBinaryString(i));
    }

    @Test
    public void test03 () {
        byte b = 2;
        b += 1;
        System.out.println(b);
    }
}
