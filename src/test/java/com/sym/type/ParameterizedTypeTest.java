package com.sym.type;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * 参数化类型{@link ParameterizedType}的测试类
 */
public class ParameterizedTypeTest {

    private Class<ParameterizedTypeBean> aClass;

    @Before
    public void init() {
        aClass = ParameterizedTypeBean.class;
    }

    /**
     * 遍历{@link ParameterizedTypeBean}的所有字段(成员变量), 然后判断它的字段是否
     * 属于参数化类型{@link ParameterizedType}
     */
    @Test
    public void foreachTest() {
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            // 获取字段的名称
            String fieldName = field.getName();
            // 获取字段的类类型(Class实例)
            Class<?> fieldClass = field.getType();
            //  获取成员变量所属的Type类型(这边特指ParameterizedType实例)
            Type fieldType = field.getGenericType();

            // 只有带有泛型的字段才属于 ParameterizedType(PS：整个字段属于 ParameterizedType 类型)
            // 如果是普通字段就直接属于 Class
            System.out.printf("%-10s", fieldName);
            System.out.printf("%-50s", fieldClass);
            System.out.printf("%-20s", fieldType instanceof ParameterizedType);
            System.out.printf("%-10s", fieldType);
            System.out.println();
        }
    }


    /**
     * 获取{@link ParameterizedTypeBean#list}变量, 只有一个泛型
     */
    @Test
    public void singleTypeTest() throws NoSuchFieldException {
        Field field = aClass.getDeclaredField("nestedList");

        // 通过 getGenericType() 方法才能获取到字段的 parameterizedType
        Type genericType = field.getGenericType();

        // 测试 parameterizedType 的基础功能
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;

            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            System.out.println(Arrays.toString(actualTypeArguments));

            Type ownerType = parameterizedType.getOwnerType();
            System.out.println(ownerType);

            Type rawType = parameterizedType.getRawType();
            System.out.println(rawType);

            String typeName = parameterizedType.getTypeName();
            System.out.println(typeName);
        }
    }


    /**
     * 获取{@link ParameterizedTypeBean#map}变量, 有两个泛型
     */
    @Test
    public void doubleTypeTest() throws NoSuchFieldException {
        Field field = aClass.getDeclaredField("map");
        // 通过 getGenericType() 方法才能获取到字段的 parameterizedType
        Type genericType = field.getGenericType();

        // 测试 parameterizedType 的基础功能
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            System.out.println(Arrays.toString(actualTypeArguments));

            Type ownerType = parameterizedType.getOwnerType();
            System.out.println(ownerType);

            Type rawType = parameterizedType.getRawType();
            System.out.println(rawType);

            String typeName = parameterizedType.getTypeName();
            System.out.println(typeName);
        }
    }


    /**
     * 通过 {@link ParameterizedType}获取参数化类型(成员变量map)的泛型的具体Class对象
     */
    @Test
    public void getActualTypeTest() throws NoSuchFieldException {
        Field field = aClass.getDeclaredField("map");
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            for (Type typeArgument : typeArguments) {
                // 通过 getActualTypeArguments()方法获取泛型的具体类型, 可能是Class, 也可能还是 ParameterizedType
                Class<?> aClass = (Class<?>) typeArgument;
                System.out.println(aClass);
            }
        }
    }


    /**
     * 通过 {@link ParameterizedType}获取参数化类型(成员变量map)的泛型的具体Class对象
     */
    @Test
    public void getNestedActualTypeTest() throws NoSuchFieldException {
        Field field = aClass.getDeclaredField("nestedList");
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            for (Type typeArgument : typeArguments) {
                // 通过 getActualTypeArguments()方法获取泛型的具体类型
                // 这里由于List的泛型还是List, 所以它还是一个 ParameterizedType
                ParameterizedType aClass = (ParameterizedType) typeArgument;
                System.out.println(aClass);
            }
        }
    }


    /**
     * 通过 {@link ParameterizedType}获取参数化类型(entry)自身的具体Class对象
     * PS：如果取成员变量 map, 则使用 getOwnerType() 返回的是 null
     * 如果取成员变量 entry, 则使用 getOwnerType() 返回的是 java.util.Map
     */
    @Test
    public void getEntryTest() throws NoSuchFieldException {
        Field field = aClass.getDeclaredField("entry");
        Type genericType = field.getGenericType();
        ParameterizedType parameterizedType = (ParameterizedType) genericType;
        // 通过 getOwnerType()方法获取到的类型就是实际的Class对象
        Type ownerType = parameterizedType.getOwnerType();
        Class<?> aClass = (Class<?>) ownerType;
        System.out.println(aClass);
    }
}
