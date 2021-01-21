package com.sym.proxy.asm;

/**
 * @author shenym
 * @date 2019/12/29 9:32
 */

public class SimpleAsmClassLoader extends ClassLoader {
    public Class<?> defineClass(String name, byte[] b) {
        // ClassLoader是个抽象类，而ClassLoader.defineClass 方法是protected的
        // 所以我们需要定义一个子类将这个方法暴露出来
        return super.defineClass(name, b, 0, b.length);
    }
}
