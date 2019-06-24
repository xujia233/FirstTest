package decorator;

/**
 * Created by xujia on 2019/5/26
 */
public class Pizza implements Food {
    @Override
    public void say() {
        System.out.println("i'm a pizza");
    }
}
