package com.sym.proxy.cglib.proxy;

import com.sym.proxy.cglib.proxy.entity.BaseClass;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib的核心, 通过 JDK 动态代理的{@link java.lang.reflect.InvocationHandler}接口一样,
 * 此拦截器会在拦截被代理类的方法为其增强逻辑, 通过
 *
 * @author shenym
 * @date 2019/12/29 9:55
 */

public class SecondMethodInterceptor implements MethodInterceptor {

    /**
     * @param obj    cglib动态生成的代理类实例, 即{@link BaseClass}的代理类
     * @param method 基类{@link BaseClass}定义的方法
     * @param args   方法的参数
     * @param proxy  cglib方法代理对象
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("拦截器2: 方法执行前....");
        Object o = proxy.invokeSuper(obj, args);
        System.out.println("拦截器2: 方法执行后....");
        return o;
    }
}
