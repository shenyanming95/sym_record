package com.sym.reflection.reflection;

import com.sym.reflection.domain.SubClassEntity;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Java反射机制之构造方法的反射
 * <p>
 * 构造器的方法,扮演重要角色的是：Constructor
 */
public class ConstructorReflectionTest {

    @Test
    public void test() {
        ConstructorReflectionTest cr = new ConstructorReflectionTest();
        SubClassEntity s = new SubClassEntity();
        /*Constructor.getConstructors()获取自己声明为public的构造器*/
        cr.getConstructors(s);
        System.out.println("=========================分割线======================");
        /*Constructor.getDeclaredConstructors()获取自己声明的所有构造器，不论权限*/
        cr.getDeclaredConstructors(s);
        System.out.println("=========================分割线======================");
        /*使用Constructor实例化对象*/
        cr.ConstructorInvoke("沈小棋");
    }

    /**
     * Constructor.getConstructors()获取自己声明的public的构造方法
     */
    private void getConstructors(Object o) {
        Class cl = o.getClass();
        Constructor[] cs = cl.getConstructors();
        Arrays.stream(cs).forEach((c) -> {
            // 获取构造器的访问修饰符
            int i = c.getModifiers();
            System.out.print(Modifier.toString(i) + " ");

            // 获取构造器的名称
            String cname = c.getName();
            System.out.print(cname + "(");

            // 获取构造器参数的Class对象,构造函数没有返回值,只有参数
            Class pts[] = c.getParameterTypes();
            for (Class cla : pts) {
                System.out.print(cla.getSimpleName() + ",");
            }

            System.out.println(")");
        });
    }

    /**
     * Constructor.getDeclaredConstructors()获取自己声明的所有构造方法(不论权限)
     */
    private void getDeclaredConstructors(Object o) {
        Class cl = o.getClass();
        Constructor[] cs = cl.getDeclaredConstructors();
        int count = 1;
        for (Constructor c : cs) {
            System.out.print(count++ + "、");
            // 获取构造器的访问修饰符
            int i = c.getModifiers();
            System.out.print(Modifier.toString(i) + " ");
            // 获取构造器的名称
            String cname = c.getName();
            System.out.print(cname + "(");
            // 获取构造器参数的Class对象,构造函数没有返回值,只有参数
            Class pts[] = c.getParameterTypes();
            for (Class cla : pts) {
                System.out.print(cla.getSimpleName() + ",");
            }
            System.out.println(")");
        }
    }

    /**
     * 通过Constructor.newInstance()可以调用有参的构造方法,实例化指定数值的对象
     */
    public void ConstructorInvoke(String name) {
        Class c = SubClassEntity.class;
        try {
            // Class.newInstance()只能调用无参的构造器
            System.out.print("Class.newInstance()调用无参构造器实例化对象：");
            SubClassEntity s1 = (SubClassEntity) c.newInstance();
            System.out.println(s1.getName());

            // Constructor可以调用有参的构造器,实例化指定数值的对象
            Constructor cs = c.getDeclaredConstructor(String.class);
            cs.setAccessible(true);// 只要是private属性都需要这行代码来取消java的安全机制
            SubClassEntity s2 = (SubClassEntity) cs.newInstance(name);
            System.out.print("Constructor.newInsrance()调用有参构造器实例化对象：");
            System.out.println(s2.getName());

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                SecurityException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
