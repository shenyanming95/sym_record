package com.sym.regular;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 * <p>
 * Created by shenym on 2019/10/22.
 */
public class RegularDemo {

    /**
     * 去除字符串指定的特殊字符和空格
     */
    @Test
    public void testOne() {
        String s = "    \b12\rasdsa\t  21  21  \n2132\f  ";
        System.out.println(s);
        Pattern pattern = Pattern.compile("\\s*|\t|\r|\n|\b\f");
        Matcher matcher = pattern.matcher(s);
        s = matcher.replaceAll(""); // 匹配正则指定的符号, 将其转换成空字符串
        System.out.println(s);
    }

    /**
     * 验证是否是URL
     */
    @Test
    public void verifyUrl(){
        String url = null;
        // URL验证规则
        String regEx ="[a-zA-z]+://[^\\s]*";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        // 字符串是否与正则表达式相匹配
        System.out.println(matcher.matches());
    }
}
