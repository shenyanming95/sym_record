package com.sym.java8.lambda.methodReference;

/**
 * 方法引用的测试类
 *
 * @Auther: shenym
 * @Date: 2018-12-11 16:13
 */
public class CountTest {
    
    public static void main(String[] args){

        // lambda体的逻辑就是为了计算两数之和
        CountInterface cf1 = (x,y)-> {return x+y;};

        // 这个逻辑和Count类的add()方法是一样,且它们的参数一样,
        // 就可以使用方法引用,如果方法是静态的,则直接"类名::方法名",
        // 如果是非静态方法,则需要实例化,用"实例对象::方法名"。
        Count count = new Count();
        CountInterface cf2 = count::add;

        // 效果是一样的
        cf1.countBothInt(1,2);
        cf1.countBothInt(1,2);

    }

}
