package com.sym.spi.service.impl;

import com.sym.spi.service.KeyGenerator;

/**
 * @author shenym
 * @date 2020/3/6 7:13
 */

public class SimpleKeyGenerator implements KeyGenerator {

    @Override
    public String generate() {
        return "java自带的spi机制";
    }

}
