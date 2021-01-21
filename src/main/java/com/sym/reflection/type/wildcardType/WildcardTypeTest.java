package com.sym.reflection.type.wildcardType;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

/**
 * 泛型表达式的类型{@link WildcardType}的测试类
 * <p>
 * Created by shenym on 2019/12/31.
 */
public class WildcardTypeTest {

    private Class<WildcardTypeBean> aClass = WildcardTypeBean.class;

    /**
     * 遍历{@link WildcardTypeBean}获取它的泛型表达式类型, 但是这种类型比较特殊, 因为它不属于
     * {@link Type}的一种具体类型, 而是需要其它类型来获取.
     */
    @Test
    public void foreachTest() {
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            // 获取属性名称
            String fieldName = field.getName();
            System.out.printf("%-10s", fieldName);
            // 获取参数化类型 parameterizedType, 因为变量是属于这种类型, 然后才能获取到泛型表达式类型
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                // 获取参数化类型 ParameterizedType 的实际泛型类型, 这个泛型就是 WildcardType
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                Type actualTypeArgument = actualTypeArguments[0];
                if (actualTypeArgument instanceof WildcardType) {
                    WildcardType wildcardType = (WildcardType) actualTypeArgument;
                    System.out.printf("%-20s", wildcardType);
                }
            }
            System.out.println();
        }
    }


    /**
     * 获取单个变量
     */
    @Test
    public void fieldTest() throws NoSuchFieldException {
        Field field = aClass.getDeclaredField("list");
        // 先将其强转为 ParameterizedType 类型
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        // 再从 ParameterizedType 类型中获取泛型表达式类型
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        WildcardType wildcardType = (WildcardType) actualTypeArguments[0];

        // 得到上边界 Type 的数组
        Type[] upperBounds = wildcardType.getUpperBounds();
        System.out.println(Arrays.toString(upperBounds));

        // 得到下边界 Type 的数组
        Type[] lowerBounds = wildcardType.getLowerBounds();
        System.out.println(Arrays.toString(lowerBounds));
    }


    /**
     * 获取单个变量
     */
    @Test
    public void field2Test() throws NoSuchFieldException {
        Field field = aClass.getDeclaredField("tEntity");
        // 先将其强转为 ParameterizedType 类型
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        // 再从 ParameterizedType 类型中获取泛型表达式类型
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        WildcardType wildcardType = (WildcardType) actualTypeArguments[0];

        // 得到上边界 Type 的数组
        Type[] upperBounds = wildcardType.getUpperBounds();
        System.out.println(Arrays.toString(upperBounds));

        // 得到下边界 Type 的数组
        Type[] lowerBounds = wildcardType.getLowerBounds();
        System.out.println(Arrays.toString(lowerBounds));
    }


}
