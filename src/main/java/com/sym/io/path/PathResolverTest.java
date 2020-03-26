package com.sym.io.path;

import org.junit.Test;

import java.util.Set;

/**
 * Created by shenym on 2019/12/20.
 */
public class PathResolverTest {

    @Test
    public void firstTest(){
        PathResolver pathResolver = new PathResolver();
        Set<Class> classes = pathResolver.parsePackageName("com.sym");
        System.out.println(classes.size());
        System.out.println(classes);
    }
}
