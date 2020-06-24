package com.sym.scanner;

import java.util.List;

/**
 * 包扫描器, 指定一个包路径, 找出旗下所有的.class文件, 并将其转换成{@link Class}
 *
 * @author shenyanming
 * Created on 2020/6/24 09:15
 */
public interface IScanner {

    /**
     * 指定包扫描目录, 扫描旗下的类文件
     *
     * @param basePackagePath 包目录
     * @return class集合
     */
    List<Class<?>> doScan(String basePackagePath);
}
