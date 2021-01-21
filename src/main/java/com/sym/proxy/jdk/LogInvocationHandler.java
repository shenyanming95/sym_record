package com.sym.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK动态代理的核心类, 就是实现{@link InvocationHandler}接口的拦截器类,
 * JVM会把所有被代理类方法的执行都换成回调{@link LogInvocationHandler#invoke(Object, Method, Object[])}
 *
 * @author shenym
 * @date 2019/12/29 9:43
 */

public class LogInvocationHandler implements InvocationHandler {

    //既然是动态代理，那我在代理的时候必须知道是在给谁代理吧
    //这个参数就是指定此时代理的是哪个类实例
    private Object target;

    public LogInvocationHandler(Object target) {
        super();
        this.target = target;
    }


    /**
     * @param proxy  代理对象实例, 表示哪个代理对象调用了method方法
     * @param method 被代理对象中的方法
     * @param args   方法的参数
     * @return 执行该方法后的返回值
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开始记录日志...");
        Object returnParam = method.invoke(target, args);
        System.out.println("结束记录日志...");
        return returnParam;
    }
}
