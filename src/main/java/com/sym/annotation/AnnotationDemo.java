package com.sym.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ym.shen
 * @date 2019/5/7 16:49
 */
public class AnnotationDemo {

    @EnableAnnotation("god job")
    public void run() {
        System.out.println("run()方法执行了...");
    }

    public static void main(String[] args) {
        // 要让注解起作用，就要先获取到注解修饰的那个类的Class对象
        Class<?> c = AnnotationDemo.class;
        // 如果你的注解是修饰在属性上，就获取Field集合；如果你的注解是修饰在方法上，就获取Method集合
        Method[] methods = c.getDeclaredMethods();
        // 遍历所有Method对象，然后找出被自定义注解修饰的方法
        for (Method method : methods) {
            if (method.isAnnotationPresent(EnableAnnotation.class)) {
                // 获取那个注解的元数据，即我们在注解设置的值
                EnableAnnotation annotation = method.getAnnotation(EnableAnnotation.class);
                String value = annotation.value();
                try {
                    // 反射调用方法，这边可以加上自己的逻辑
                    System.out.println(value);
                    method.invoke(new AnnotationDemo(), null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
