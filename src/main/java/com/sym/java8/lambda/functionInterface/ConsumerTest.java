package com.sym.java8.lambda.functionInterface;

import java.util.function.Consumer;

/**
 * lambda表达式，内置4大函数式接口之消费型接口Consumer
 *
 * @Auther: shenym
 * @Date: 2018-12-11 13:45
 */
public class ConsumerTest {

    public static <T> void consumer(T t, Consumer<T> consumer) {
        consumer.accept(t);
    }

    public static void main(String[] args){
        ConsumerTest.consumer("测试",(t)-> System.out.println(t));
    }
}
