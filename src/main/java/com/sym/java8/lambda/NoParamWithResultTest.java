package com.sym.java8.lambda;

/**
 * 无参有返回值的函数式接口的测试类
 *
 * @Auther: shenym
 * @Date: 2018-12-11 14:26
 */
public class NoParamWithResultTest {

    public static void main(String[] args) {

        // 无参用()表示,代码只有一行可以省略{},如果省略了{}一定要省略掉return关键字和分号;
        NoParamWithResult nr = ()-> "无参有返回值";
        // 如果不省略{},则return关键字和分号;不能省略
        NoParamWithResult nw = ()-> { return "无参有返回值";};

        /* 再调用接口 */
        System.out.println(nr.run());
        System.out.println(nw.run());

    }

}
