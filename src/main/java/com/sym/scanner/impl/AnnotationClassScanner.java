package com.sym.scanner.impl;

import com.sym.scanner.AbstractScanner;

import java.lang.annotation.Annotation;

/**
 * 类带有指定注解才可以被扫描到
 *
 * @author shenyanming
 * Created on 2020/6/24 10:37
 */
public class AnnotationClassScanner extends AbstractScanner {

    private Class<? extends Annotation> classType;

    public AnnotationClassScanner(Class<? extends Annotation> classType){
        super();
        this.classType = classType;
    }

    public AnnotationClassScanner(Class<Annotation> classType, ClassLoader classLoader){
        super(classLoader);
        this.classType = classType;
    }

    @Override
    protected boolean filter(Class<?> c) {
        return c.isAnnotationPresent(classType);
    }
}
