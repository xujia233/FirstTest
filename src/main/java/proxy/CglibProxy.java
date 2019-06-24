package proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib动态代理 主要运用于目标对象未实现接口的场景
 * Created by xujia on 2019/4/17
 */
public class CglibProxy implements MethodInterceptor {

    private Object target;

    /**
     * 给外界调用返回代理类
     * @param target
     * @return
     */
    public Object getInstance(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        // 设置父类
        enhancer.setSuperclass(this.target.getClass());
        // 设置回调函数
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("before say");
        Object result = method.invoke(target, args);
        System.out.println("after say");
        return result;
    }
}
