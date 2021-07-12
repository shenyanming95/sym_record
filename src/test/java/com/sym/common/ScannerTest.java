package com.sym.common;

import com.sym.scanner.IScanner;
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

}
