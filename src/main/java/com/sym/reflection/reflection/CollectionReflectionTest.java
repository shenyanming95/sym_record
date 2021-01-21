package com.sym.reflection.reflection;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 集合的反射
 *
 * @Auther: shenym
 * @Date: 2019-01-07 10:19
 */
public class CollectionReflectionTest {

    @Test
    public void testOne() {

        // 创建泛型为string的List
        List<String> stringList = new ArrayList<>();

        // 此时如果要给 stringList 添加整型数据，会报错
        // stringList.add(20);

        Class c = stringList.getClass();

        try {
            // 获取list.add()方法对应的Method对象
            Method method = c.getDeclaredMethod("add", Object.class);

            // 添加数据
            method.invoke(stringList, 10);
            method.invoke(stringList, 20);

            // 查看是否添加成功
            System.out.println("集合长度：" + stringList.size());
            // 注意：此时遍历集合，不能使用foreach，会抛出 java.lang.ClassCastException 异常
            // 只能通过for循环
            for (int i = 0, len = stringList.size(); i < len; i++) {
                Object obj = stringList.get(i);
                System.out.println(obj.getClass()); // 执行结果：class java.lang.Integer
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

}
