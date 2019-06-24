package impl;

import proxy.People;

/**
 * Created by xujia on 2019/4/15
 */
public class Girl implements People {

    @Override
    public void say() {
        System.out.println("i'm a girl");
    }
}
