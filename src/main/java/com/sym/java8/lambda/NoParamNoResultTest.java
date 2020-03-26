package com.sym.java8.lambda;

/**
 * 无参无返回值的函数式接口的测试类
 *
 * @Auther: shenym
 * @Date: 2018-12-11 14:23
 */
public class NoParamNoResultTest {

    public static void main(String[] args) {

        // 无参用()表示,lambda体只有一行代码的可以省略括号{}
        NoParamNoResult nr = () -> System.out.println("无参无返回值");
        nr.say();

    }
}
