package com.sym.reflection.reflection;

import com.sym.reflection.domain.LabelEntity;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 方法的反射
 *
 * @Auther: shenym
 * @Date: 2019-01-04 14:28
 */
public class MethodReflectionTest {

    @Test
    public void testOne() {

        try {
            Class c = LabelEntity.class;
            // 根据方法名和方法参数，获取Method对象，如果方法参数为不定参数，可以传递数组的Class对象
            Method method1 = c.getDeclaredMethod("count", int[].class);
            // 如果方法没有参数，就不用传参
            Method method2 = c.getDeclaredMethod("getLabelId");

            // 如果方法的访问级别设置为false，则需要先取消Java的访问检查
            // method1.setAccessible(true);
            // method2.setAccessible(true);

            LabelEntity labelBean = new LabelEntity();

            // invoke()方法需要指定调用方法的对象和方法所需的参数
            int[] array = {1,2,3};
            Object result1 = method1.invoke(labelBean, array);
            Object result2 = method2.invoke(labelBean);

            System.out.println(result1);
            System.out.println(result2);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
