package com.sym.java8.lambda;

/**
 * 无参有返回值的函数式接口
 *
 * @Auther: shenym
 * @Date: 2018-12-11 14:25
 */
@FunctionalInterface
public interface NoParamWithResult {

    String run();

    default void go(){
        System.out.println("666");
    }

}
