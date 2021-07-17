package com.sym.quickstart;

import com.sym.scanner.IScanner;
import org.junit.Test;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * @author shenyanming
 * Created on 2021/7/17 15:36.
 */

public class ReflectionsTest {

    @Test
    public void test01() {
        Reflections reflections = new Reflections("com.sym");
        Set<Class<? extends IScanner>> types = reflections.getSubTypesOf(IScanner.class);
        types.forEach(c -> {
            System.out.print(c.getName() + "\t");
            System.out.println(Modifier.isAbstract(c.getModifiers()));
        });
    }

}
