package com.sym.reflection.reflection;

import com.sym.reflection.domain.SubClassEntity;
import org.junit.Test;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Java反射机制之成员变量的反射
 * <p>
 * 成员变量的反射,扮演的重要类角色：Field
 */
public class FieldReflectionTest {

    @Test
    public void test() {
        FieldReflectionTest fr = new FieldReflectionTest();
        SubClassEntity s = new SubClassEntity();
        /*Field.getFields()获取子类和父类声明为public的成员变量*/
        fr.getFields(s);
        System.out.println("==============================分割线==========================");
        /*Field.getDeclaredFields()获取子类自己声明的所有成员变量,不论权限但不包括父类*/
        fr.getDeclaredFields(s);
        System.out.println("==============================分割线==========================");
        /*反射成员变量的调用*/
        fr.fieldInvoke();
    }


    /* Field.getFields()获取子类和父类声明为public的方法 */
    private void getFields(Object o) {
        Class c = o.getClass();
        Field[] fs = c.getFields();
        int count = 1;
        for (Field f : fs) {
            // 获取成员变量的修饰符
            System.out.print(count++ + ".");
            int i = f.getModifiers();
            System.out.print(Modifier.toString(i) + " ");
            // 获取成员变量类型的Class对象
            Class paramType = f.getType();
            String typeName = paramType.getSimpleName();
            System.out.print(typeName + " ");
            // 获取成员变量的名称
            String paramName = f.getName();// 获取成员变量的名称
            System.out.println(paramName);
        }

    }

    /* Field.getDeclaredFields()获取子类的所有方法 */
    private void getDeclaredFields(Object o) {
        Class c = o.getClass();
        Field[] fs = c.getDeclaredFields();
        int count = 1;
        for (Field f : fs) {
            // 获取成员变量的修饰符
            System.out.print(count++ + "，");
            int i = f.getModifiers();
            System.out.print(Modifier.toString(i) + " ");
            // 获取成员变量类型的Class对象
            Class paramType = f.getType();
            String typeName = paramType.getSimpleName();
            System.out.print(typeName + " ");
            // 获取成员变量的名称
            String paramName = f.getName();// 获取成员变量的名称
            System.out.println(paramName);
        }
    }

    /* 成员变量反射的操作 */
    private void fieldInvoke() {
        SubClassEntity s = new SubClassEntity();
        Class c = s.getClass();
        /* 前2个方法都是获取多个成员变量,也可以获取单个的成员变量 */
        try {
            Field[] fs2 = c.getDeclaredFields();
            AccessibleObject.setAccessible(fs2, true);

            //给定成员变量名称就可以获取单个的成员变量
            Field f = c.getDeclaredField("name");
            //通过get方法可以获取成员变量的值,即使他是private类型,前提是要执行下面这行代码
            f.setAccessible(true);
            Object o = f.get(s);
            System.out.println("反射操作前,name=" + o);
            //通过set方法可以改变成员变量的值
            f.set(s, "我在运行中被修改的");
            System.out.println("反射操作后,name=" + s.getName());
        } catch (NoSuchFieldException | IllegalArgumentException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
