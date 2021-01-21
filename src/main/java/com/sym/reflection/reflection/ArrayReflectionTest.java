package com.sym.reflection.reflection;

import org.junit.Test;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 数组的反射
 *
 * @Auther: shenym
 * @Date: 2019-01-07 9:46
 */
public class ArrayReflectionTest {


    @Test
    public void testTwo() {

        int[] array = {10,20,30,40};

        // 获取数组的Class对象
        Class c = array.getClass();
        // 或者这样获取：int[].class;
        Class c1 = int[].class;

        System.out.println("是否数组："+c.isArray()); //执行结果：true
        System.out.println("什么类型的数组："+c.getComponentType()); // 执行结果：int
        System.out.println("是否为基本类型："+c.isPrimitive()); // 执行结果：false
        System.out.println("数组长度："+ Array.getLength(array)); // 执行结果：4

        // 获取数组指定下标的元素
        Object ret = Array.get(array, 1);
        System.out.println(ret); // 执行结果：20

        // 实例化指定类型、指定长度的数组
        Object obj = Array.newInstance(String.class, 7);
        String[] strArray = (String[])obj;

        // 为数组赋值
        Array.set(strArray,0,"中");
        Array.set(strArray,1,"化");
        Array.set(strArray,2,"人");
        Array.set(strArray,3,"民");
        Array.set(strArray,4,"共");
        Array.set(strArray,5,"和");
        Array.set(strArray,6,"国");

        Arrays.stream(strArray).forEach(System.out::print); // 执行结果：中化人民共和国

    }
}
