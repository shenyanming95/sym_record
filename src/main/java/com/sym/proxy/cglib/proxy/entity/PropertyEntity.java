package com.sym.proxy.cglib.proxy.entity;

import lombok.AllArgsConstructor;

/**
 * 构造方法有参数的类
 * <p>
 * Created by shenym on 2020/1/15.
 */
@AllArgsConstructor
public class PropertyEntity {

    private int id;
    private String name;

    public String getInfo() {
        return this.id + ", " + this.name;
    }

}
