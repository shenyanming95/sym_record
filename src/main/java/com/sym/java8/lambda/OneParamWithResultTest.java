package com.sym.java8.lambda;

/**
 * 有一个参有返回值的函数式接口的测试类
 *
 * @Auther: shenym
 * @Date: 2018-12-11 14:40
 */
public class OneParamWithResultTest {
    
    public static void main(String[] args){

        // 参数写在箭头符号->左侧的括号里,不用写参数类型,只要用
        // 一个变量名表示参数即可
        OneParamWithResult wr = (x)->{
            Integer ret = x.intValue()>>3;
            return ret;
        };

        // 调用接口方法
        int ret = wr.run(15);
        System.out.println(ret);
        wr.go();

    }

}
