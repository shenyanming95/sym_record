package com.sym.reflection.api;

import com.sym.reflection.domain.SuperClassEntity;
import lombok.Data;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 介绍{@link Class}的所有API
 * <p>
 * Created by shenym on 2019/8/23.
 */
public class ClassApiTest {

    /**
     * is*开头的所有方法解析
     */
    @Test
    public void firstTest() {
        Class<SuperClassEntity> c = SuperClassEntity.class;

        /* 判断此Class对象是否为一个注解 */
        boolean isAnnotation = c.isAnnotation();

        /* 用来判断方法参数指定对象是否是此Class对象的一个实例 */
        boolean isInstance = c.isInstance(new SuperClassEntity());

        /* 用来判断此Class对象是否为数组 */
        boolean isArray = c.isArray();

        /* 用来判断此Class对象是否为八大基本类型或者void关键词 */
        boolean isPrimitive = c.isPrimitive();

        /* 用来判断此Class对象是否带有指定注解类型 */
        boolean isAnnotationPresent = c.isAnnotationPresent(Data.class);

        /* 当且仅当此Class对象为匿名实现类,此方法才会返回true */
        boolean isAnonymousClass = c.isAnonymousClass();

        /* 判断此Class对象与方法参数指定的Class对象所表示的类或接口(全部)是否一样
         *  或者此Class对象是否是指定Class对象的超类或超接口 */
        boolean isAssignableFrom = c.isAssignableFrom(Object.class);

        /* 判断此Class对象是否为枚举 */
        boolean isAnEnum = c.isEnum();

        /* 判断此Class对象是否为接口 */
        boolean anInterface = c.isInterface();

        /* 判断此Class是否为一个局部类(在方法内部定义的类) */
        boolean isLocalClass = c.isLocalClass();

        /* 判断此Class是否为一个成员内部类 */
        boolean isMemberClass = c.isMemberClass();
    }

    @Test
    public void secondTest() {
        // 在方法内部定义的类就是局部类
        @Data
        class SymLocalClass {
            private int id;
        }
        Class<SymLocalClass> c = SymLocalClass.class;
        System.out.println(c.isLocalClass());
    }

    @Test
    public void thirdTest() {
        // 在一个类里面定义另一个类
        Class<SymMemberClass> c = SymMemberClass.class;
        System.out.println(c.isMemberClass());
    }


    @Test
    public void arrayCopyTest() {
        // 准备原始数组
        int[] oldIntArray = {1, 2, 3, 4, 5, 6};

        // 获取数组的长度
        int length = Array.getLength(oldIntArray);

        // 获取数据的Class实例
        Class<?> aClass = oldIntArray.getClass();

        // 获取数组的类型
        Class<?> typeClass = aClass.getComponentType();

        // 使用Array实例化一个指定类型、指定长度的数组
        // 实例化一个新的数组
        Object newArray = Array.newInstance(typeClass, length);

        // 调用系统的本地方法拷贝数组
        System.arraycopy(oldIntArray, 0, newArray, 0, length);

        // 验证
        System.out.println(oldIntArray == newArray);
        System.out.println(Arrays.toString((int[]) newArray));
    }

    /**
     * 模拟一个内部类
     */
    @Data
    static class SymMemberClass {
        private String name;
    }

}
