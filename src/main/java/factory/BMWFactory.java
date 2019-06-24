package factory;

/**
 * Created by xujia on 2019/5/26
 */
public class BMWFactory implements Factory{
    @Override
    public Car createCar() {
        return new BMW();
    }
}
