package factory;

/**
 * Created by xujia on 2019/5/26
 */
public class CarFactory extends AbstractFactory {

    public static final int BMW_TYPE = 1;
    public static final int BENZ_TYPE = 2;

    @Override
    public Car getCar(int type) {
        switch (type) {
            case BMW_TYPE :
                return new BMWFactory().createCar();
            case BENZ_TYPE :
                return new BenZFactory().createCar();
            default :
                return null;
        }
    }
}
