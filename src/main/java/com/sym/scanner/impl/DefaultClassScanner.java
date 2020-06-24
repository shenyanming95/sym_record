package com.sym.scanner.impl;

import com.sym.scanner.AbstractScanner;

/**
 * 默认扫描器, 会加载所有的class
 *
 * @author shenyanming
 * Created on 2020/6/24 10:19
 */
public class DefaultClassScanner extends AbstractScanner {
    @Override
    protected boolean filter(Class<?> c) {
        return true;
    }
}
