package com.sym.proxy.cglib.proxy.entity;

/**
 * 通过 cglib 为该类做代理.  cglib区别于jdk动态代理, 它不仅可以作用于接口,
 * 也可以作用于类, 前提是类及其它的方法未被 final 关键字修饰. 因为cglib底层
 * 是通过 ASM 框架以字节码增强技术为基类动态生成一个代理子类, 如果类和它的方法
 * 被定义为 final, 则没办法继承和重写.
 *
 * @author shenym
 * @date 2019/12/29 9:50
 */

public class BaseClass {

    /**
     * 无参无返回值
     */
    public void doSomething() {
        System.out.println("基类方法 doSomething() 执行...");
    }

    /**
     * 无参无返回值
     */
    public void doRun() {
        System.out.println("基类方法 doRun() 执行...");
    }

    /**
     * 定义一个 final 类型的方法, 这个方法cglib是没办法代理的
     */
    public final String toJson() {
        System.out.println(66);
        return "{}";
    }

}
