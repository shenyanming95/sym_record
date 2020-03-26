package com.sym.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解例子
 * Created by 沈燕明 on 2019/5/7 16:48.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String value() default "default";
}
