package com.sym.proxy.cglib.proxy;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * 回调过滤器，不同的方法执行 Callback[] 数组的不同对象
 *
 * @author shenym
 * @date 2019/12/29 10:01
 */
public class BaseCallbackFilter implements CallbackFilter {

    /**
     * 指定说不同的方法名, 调用数组的不同拦截器;
     * 例如：返回0, 表示调用数组第一个元素
     */
    @Override
    public int accept(Method method) {
        if ("doSomething".equals(method.getName())) {
            return 0;
        } else {
            return 1;
        }
    }
}
