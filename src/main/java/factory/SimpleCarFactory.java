package factory;

/**
 * Created by xujia on 2019/5/26
 */
public class SimpleCarFactory {

    public static final int BMW_TYPE = 1;
    public static final int BENZ_TYPE = 2;

    public static Car createCar(int type) {
        switch (type) {
            case BMW_TYPE :
                return new BMW();
            case BENZ_TYPE :
                return new Benz();
            default :
                return null;
        }
    }
}
