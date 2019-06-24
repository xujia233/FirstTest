package factory;

/**
 * Created by xujia on 2019/5/26
 */
public class Benz implements Car {
    @Override
    public void create() {
        System.out.println("奔驰已经建好");
    }
}
