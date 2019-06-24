package factory;

import org.junit.Test;

/**
 * Created by xujia on 2019/5/26
 */
public class FactoryTest {

    @Test
    public void test() {
        Car bmw = SimpleCarFactory.createCar(SimpleCarFactory.BMW_TYPE);
        bmw.create();
        Car benz = SimpleCarFactory.createCar(SimpleCarFactory.BENZ_TYPE);
        benz.create();
    }

    @Test
    public void test01() {
        BMWFactory bmwFactory = new BMWFactory();
        bmwFactory.createCar().create();
        BenZFactory benZFactory = new BenZFactory();
        benZFactory.createCar().create();
    }

    @Test
    public void test02() {
        CarFactory carFactory = new CarFactory();
        Car bmw = carFactory.getCar(CarFactory.BMW_TYPE);
        Car benz = carFactory.getCar(CarFactory.BENZ_TYPE);
    }
}
