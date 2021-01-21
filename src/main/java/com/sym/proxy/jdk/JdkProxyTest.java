package com.sym.proxy.jdk;

import com.sym.proxy.jdk.service.Car;
import com.sym.proxy.jdk.service.MoveAble;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * @author shenym
 * @date 2019/12/29 9:47
 */

public class JdkProxyTest {

    @Test
    public void mainTest(){
        Car car = new Car();
        LogInvocationHandler logInvocationHandler = new LogInvocationHandler(car);
        Class<?> aClass = car.getClass();

        //创建代理对象，proxy就是Car类的代理
        MoveAble proxy = (MoveAble)
                Proxy.newProxyInstance(aClass.getClassLoader(),
                        aClass.getInterfaces(), logInvocationHandler);
        //测试无参无返回值
        proxy.move();
        System.out.println();

        //测试有参有返回值
        String response = proxy.speed("40");
        System.out.println(response);
    }
}
