package com.sym.reflection.type.genericArrayType;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 数组化类型{@link GenericArrayTypeBean}的测试类
 * <p>
 * Created by shenym on 2019/12/31.
 */
public class GenericArrayTypeTest {

    private Class<GenericArrayTypeBean> aClass = GenericArrayTypeBean.class;

    /**
     * 遍历{@link GenericArrayTypeBean}的所有字段(成员变量), 然后判断它的字段是否
     * 属于数组化类型{@link GenericArrayType}
     */
    @Test
    public void foreachTest() {
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            // 获取字段的名称
            String fieldName = field.getName();
            // 获取字段的类类型(Class实例)
            Class<?> fieldClass = field.getType();
            //  获取成员变量所属的Type类型(这边特指 GenericArrayType)
            Type fieldType = field.getGenericType();

            // 只有带有泛型的数组才属于 GenericArrayType(PS：整个数组变量属于 GenericArrayType 类型)
            // 如果是普通字段就直接属于 Class
            System.out.printf("%-20s", fieldName);
            System.out.printf("%-40s", fieldClass);
            System.out.printf("%-10s", fieldType instanceof GenericArrayType);
            System.out.printf("%-40s", fieldType);
            System.out.println();
        }
    }


    @Test
    public void fieldTest() throws NoSuchFieldException {
        Field field = aClass.getDeclaredField("tArray");
        Type genericType = field.getGenericType();
        if (genericType instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) genericType;

            String typeName = genericArrayType.getTypeName();
            System.out.println(typeName);

            Type genericComponentType = genericArrayType.getGenericComponentType();
            System.out.println(genericComponentType);
        }
    }


    @Test
    public void field2Test() throws NoSuchFieldException {
        Field field = aClass.getDeclaredField("listArray");
        Type genericType = field.getGenericType();
        if (genericType instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) genericType;

            String typeName = genericArrayType.getTypeName();
            System.out.println(typeName);

            // 获取数组实际元素的类型, 这里得到的是：ParameterizedType
            Type genericComponentType = genericArrayType.getGenericComponentType();
            System.out.println((genericComponentType instanceof ParameterizedType) + "\t" + genericComponentType);
        }
    }
}
