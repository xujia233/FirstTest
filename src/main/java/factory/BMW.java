package factory;

/**
 * Created by xujia on 2019/5/26
 */
public class BMW implements Car{
    @Override
    public void create() {
        System.out.println("宝马已经建好");
    }
}
