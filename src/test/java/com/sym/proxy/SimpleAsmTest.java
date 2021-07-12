package com.sym.proxy;

import com.sym.proxy.asm.SimpleAsmClassLoader;
import org.junit.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 使用 ASM 手动动态生成一个 HelloWorld 类, 并且执行其 main() 方法, 打印字符串
 *
 * @author shenym
 * @date 2019/12/29 9:31
 */

public class SimpleAsmTest {

    /**
     * 调用方法执行
     */
    @Test
    public void mainTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        byte[] bytes = this.generateByteArray();
        SimpleAsmClassLoader classLoader = new SimpleAsmClassLoader();
        // 加载使用 ASM 动态生成的类文件
        Class<?> aClass = classLoader.defineClass("com.sym.asm.HelloWorld", bytes);
        System.out.println(aClass);
        // 反射获取 main 方法
        Method main = aClass.getMethod("main", String[].class);
        // 调用 main 方法
        main.invoke(null, new Object[]{new String[]{}});
    }


    /**
     * 生成类和方法的字节数组
     */
    private byte[] generateByteArray(){
        ClassWriter cw = new ClassWriter(0);
        // 定义对象头：版本号、修饰符、全类名、签名、父类、实现的接口
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "com/sym/asm/HelloWorld", null, "java/lang/Object", null);
        // 添加方法：修饰符、方法名、描述符、签名、抛出的异常
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        // 执行指令：获取静态属性
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        // 加载常量 load constant
        mv.visitLdcInsn("HelloWorld!");
        // 调用方法
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        // 返回
        mv.visitInsn(Opcodes.RETURN);
        // 设置栈大小和局部变量表大小
        mv.visitMaxs(2, 1);
        // 方法结束
        mv.visitEnd();
        // 类完成
        cw.visitEnd();
        // 生成字节数组
        return cw.toByteArray();
    }
}
