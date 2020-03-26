package com.sym.java8.lambda;

/**
 * 多参有返回值的函数式接口的测试类
 *
 * @Auther: shenym
 * @Date: 2018-12-11 14:49
 */
public class MoreParamWithResultTest {

    public static void main(String[] args){

        // 多个参数用逗号隔开
        MoreParamWithResult mr = (s1,s2)->{
            return s1.concat(s2);
        };

        // 调用接口
        String retStr = mr.run("多参", "有返回值");
        System.out.println(retStr);

    }

}
