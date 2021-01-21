package com.sym.proxy.jdk.service;

/**
 * JDK的动态代理, 仅支持接口, 因此需要先定义父级接口.
 *
 * @author shenym
 * @date 2019/12/29 9:40
 */

public interface MoveAble {
    /**
     * 无参无返回值
     */
    void move();

    /**
     * 带参有返回值
     */
    String speed(String param);
}
