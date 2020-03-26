package com.sym.java8.lambda.constructorReference;

/**
 * 构造引用的测试类
 *
 * @Auther: shenym
 * @Date: 2018-12-11 16:28
 */
public class UserTest {

    public static void main(String[] args){

        // 函数式接口的抽象方法就是为了实例化一个User
        UserInterface u1 = (a)-> {
            User user = new User(a);
            return user;
        };

        // 这个实例化方式直接在User存在,就可以使用构造引用
        UserInterface u2 = User::new;
        
        // 效果是一样的
        u1.newUser(1);
        u2.newUser(1);

    }

}
