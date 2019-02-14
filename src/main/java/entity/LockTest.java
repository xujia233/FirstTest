package entity;

/**
 * 死锁测试类 内部类无法使用静态属性特独立出来
 * Created by xujia on 2019/2/14
 */
public class LockTest {

    public static final Object lockA = new Object();
    public static final Object lockB = new Object();
}
