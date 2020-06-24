package com.sym.scanner.impl;

import com.sym.scanner.AbstractScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 类中只要有一个方法带有指定注解, 就可以扫描到
 *
 * @author shenyanming
 * Created on 2020/6/24 10:45
 */
public class AnnotationMethodScanner extends AbstractScanner {

    private List<Class<? extends Annotation>> annotationList;

    public AnnotationMethodScanner(Class<? extends Annotation> aClass) {
        this(Collections.singletonList(aClass));
    }

    public AnnotationMethodScanner(List<Class<? extends Annotation>> annotationList) {
        this.annotationList = new ArrayList<>(annotationList);
    }

    @Override
    protected boolean filter(Class<?> c) {
        Method[] declaredMethods = c.getDeclaredMethods();
        for (Method method : declaredMethods) {
            for (Class<? extends Annotation> aClass : annotationList) {
                boolean present = method.isAnnotationPresent(aClass);
                if (present) {
                    return true;
                }
            }
        }
        return false;
    }
}
