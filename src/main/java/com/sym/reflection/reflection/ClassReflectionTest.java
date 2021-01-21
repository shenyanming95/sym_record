package com.sym.reflection.reflection;

import com.sym.reflection.domain.SubClassEntity;
import org.junit.Test;

/**
 * 获取一个Class对象的三种方式
 * <p>
 * Created by 沈燕明 on 2019/1/7.
 */
public class ClassReflectionTest {

    @Test
    public void test() {
        /*测试，每个类型的Class对象有且仅有一个*/
        ClassReflectionTest cc = new ClassReflectionTest();
        cc.createByclass();
        cc.createByClassForName();
        cc.createByGetClass();

        /*newInstance()方法实例化不同的对象*/
        cc.instanceTest();
    }

    /**
     * 通过Class.forName()方法获取,需要捕获ClassNotFoundException
     */
    private void createByClassForName() {
        try {
            Class c = Class.forName("com.domain.SubClassEntity");
            System.out.println(c.hashCode());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 通过隐式参数.class来获取
     */
    private void createByclass() {
        Class c = SubClassEntity.class;
        System.out.println(c.hashCode());
    }


    /**
     * 通过实例对象的getClass()方法获取
     */
    public void createByGetClass() {
        SubClassEntity stu = new SubClassEntity();
        Class c = stu.getClass();
        System.out.println(c.hashCode());
    }


    /**
     * 通过newInstance()方法可以实例化对象,它会调用对应的无参构造方法
     */
    public void instanceTest() {
        Class c = SubClassEntity.class;
        try {
            SubClassEntity s1 = (SubClassEntity) c.newInstance();
            SubClassEntity s2 = (SubClassEntity) c.newInstance();
            System.out.println("s1=" + s1 + ",s2=" + s2);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
