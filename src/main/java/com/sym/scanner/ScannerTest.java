package com.sym.scanner;

import com.sym.annotation.EnableAnnotation;
import com.sym.scanner.impl.AnnotationClassScanner;
import com.sym.scanner.impl.AnnotationMethodScanner;
import com.sym.scanner.impl.DefaultClassScanner;
import org.junit.Test;

import java.util.List;

/**
 * @author shenyanming
 * Created on 2020/6/24 10:39
 */
public class ScannerTest {

    @Test
    public void test01(){
        IScanner scanner = new DefaultClassScanner();
        List<Class<?>> classList = scanner.doScan("com.sym");
        classList.forEach(System.out::println);
    }

    @Test
    public void test02(){
        IScanner scanner = new AnnotationClassScanner(EnableAnnotation.class);
        List<Class<?>> classList = scanner.doScan("com.sym.annotation");
        classList.forEach(System.out::println);
    }

    @Test
    public void test03(){
        IScanner scanner = new AnnotationMethodScanner(EnableAnnotation.class);
        List<Class<?>> classList = scanner.doScan("com.sym.annotation");
        classList.forEach(System.out::println);
    }
}
