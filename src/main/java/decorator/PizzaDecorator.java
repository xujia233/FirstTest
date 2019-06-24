package decorator;

/**
 * Created by xujia on 2019/5/26
 */
public class PizzaDecorator implements Food {

    private Food food;

    public PizzaDecorator(Food food) {
        this.food = food;
    }

    @Override
    public void say() {
        // 新增的业务代码
        System.out.println("加点牛肉");
        food.say();
    }
}
