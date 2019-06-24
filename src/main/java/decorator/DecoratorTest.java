package decorator;

import org.junit.Test;

/**
 * Created by xujia on 2019/5/26
 */
public class DecoratorTest {

    @Test
    public void test() {
        PizzaDecorator pizzaDecorator = new PizzaDecorator(new Pizza());
        pizzaDecorator.say();
    }
}
