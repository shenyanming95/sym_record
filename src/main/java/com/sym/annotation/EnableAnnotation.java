package com.sym.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解例子
 *
 * @author ym.shen
 * @date 2019/5/7 16:48
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableAnnotation {
    String value() default "default";
    boolean filter() default false;
    int timeOut() default 1000;
}
