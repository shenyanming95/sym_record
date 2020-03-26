package com.sym.spi.service.impl;

import com.sym.spi.service.KeyGenerator;

/**
 * @author shenym
 * @date 2020/3/6 7:14
 */

public class CompositeKeyGenerator implements KeyGenerator {

    @Override
    public String generate() {
        return "hello world";
    }

}
