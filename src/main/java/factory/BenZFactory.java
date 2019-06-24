package factory;

/**
 * Created by xujia on 2019/5/26
 */
public class BenZFactory implements Factory {
    @Override
    public Car createCar() {
        return new Benz();
    }
}
