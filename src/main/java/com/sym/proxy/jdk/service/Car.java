package com.sym.proxy.jdk.service;


import java.util.Random;

/**
 * @author shenym
 * @date 2019/12/29 9:42
 */

public class Car implements MoveAble {

    @Override
    public void move() {
        long begin = System.currentTimeMillis();
        System.out.println("汽车开始行驶...");
        try {
            System.out.println("汽车行驶中...");
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("汽车结束行驶...用时：" + (end - begin) + "毫秒");
    }

    @Override
    public String speed(String param) {
        return "汽车加速了：" + param + " km/h";
    }
}
