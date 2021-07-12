package com.sym.type;

import com.sym.type.bean.TypeBean;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

/**
 * 类型变量{@link TypeVariable}的测试类
 */
public class TypeVariableTest {

    /**
     * 未指定泛型的{@link TypeVariableBean}的类型变量
     */
    @Test
    public void foreachTest() {
        Class<TypeVariableBean> aClass = TypeVariableBean.class;
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            // 获取成员变量的名称
            String fieldName = field.getName();
            // 获取成员变量所属的Class类型
            Class<?> fieldClass = field.getType();
            // 获取成员变量所属的Type类型(这边特指 TypeVariable)
            Type fieldType = field.getGenericType();

            // 如果用在类上的泛型才属于类型变量 TypeVariable, 如 Map<K,V>, 其中的K和V就是类型变量
            // 如果是普通字段就直接属于 Class
            System.out.printf("%-10s", fieldName);
            System.out.printf("%-50s", fieldClass);
            System.out.printf("%-20s", fieldType instanceof TypeVariable);
            System.out.printf("%-10s", fieldType);
            System.out.println();
        }
    }


    /**
     * 指定泛型的{@link TypeVariableBean}的类型变量
     */
    @Test
    public void foreach2Test() {
        TypeVariableBean<TypeBean, String> bean = new TypeVariableBean<>();
        Class<? extends TypeVariableBean> aClass = bean.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            // 获取成员变量的名称
            String fieldName = field.getName();
            // 获取成员变量所属的Class类型
            Class<?> fieldClass = field.getType();
            // 获取成员变量所属的Type类型(这边特指 TypeVariable)
            Type fieldType = field.getGenericType();

            // 如果用在类上的泛型才属于类型变量 TypeVariable, 如 Map<K,V>, 其中的K和V就是类型变量
            // 如果是普通字段就直接属于 Class
            System.out.printf("%-10s", fieldName);
            System.out.printf("%-50s", fieldClass);
            System.out.printf("%-20s", fieldType instanceof TypeVariable);
            System.out.printf("%-10s", fieldType);
            System.out.println();
        }
    }


    @Test
    public void singleFieldTest() throws NoSuchFieldException {
        Class<TypeVariableBean> aClass = TypeVariableBean.class;
        Field field = aClass.getDeclaredField("key");
        Type genericType = field.getGenericType();
        if (genericType instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) genericType;

            String name = typeVariable.getName();
            System.out.println(name);

            // 返回当前类型变量 TypeVariable 上边界的 Type 对象的数组, 如果未指定则都以 Object 表示
            Type[] bounds = typeVariable.getBounds();
            System.out.println(Arrays.toString(bounds));

            // 返回声明当前类型变量 TypeVariable 所在类的类型 Type
            GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
            System.out.println(genericDeclaration);
        }
    }


    @Test
    public void singleField2Test() throws NoSuchFieldException {
        Class<TypeVariableBean> aClass = TypeVariableBean.class;
        Field field = aClass.getDeclaredField("value");
        Type genericType = field.getGenericType();
        if (genericType instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) genericType;

            String name = typeVariable.getName();
            System.out.println(name);

            // 返回当前类型变量 TypeVariable 上边界的 Type 对象的数组, 如果未指定则都以 Object 表示
            Type[] bounds = typeVariable.getBounds();
            System.out.println(Arrays.toString(bounds));

            // 返回声明当前类型变量 TypeVariable 所在类的类型 Type
            GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
            System.out.println(genericDeclaration);
        }
    }

}
