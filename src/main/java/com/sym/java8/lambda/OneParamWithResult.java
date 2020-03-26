package com.sym.java8.lambda;

/**
 * 有一个参有返回值的函数式接口
 *
 * @Auther: shenym
 * @Date: 2018-12-11 14:37
 */
@FunctionalInterface
public interface OneParamWithResult {

    Integer run(Integer i);

    default String go(){
        return "default关键字是Java8新加的关键字";
    }

}
