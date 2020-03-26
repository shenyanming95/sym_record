package com.sym.spi;

import com.sym.spi.service.KeyGenerator;

import java.util.ServiceLoader;

/**
 * SPI, 全称为 Service Provider Interface, 是一种服务发现机制.
 * 它通过在ClassPath路径下的META-INF/services文件夹查找文件, 自动加载文件里所定义的类.
 * 有两个要求：
 * 1.必须在ClassPath:META-INF/services目录下, 创建与接口全类名一样的文件
 * 2.文件的每行表示一个接口实现类, 也必须要使用实现类的全类名
 * 3.通过Java自带的{@link ServiceLoader}加载即可
 *
 * @author shenym
 * @date 2020/3/6 7:16
 */

public class SpiDemo {
    public static void main(String[] args) {
        ServiceLoader<KeyGenerator> serviceLoader = ServiceLoader.load(KeyGenerator.class);
        for(KeyGenerator keyGenerator : serviceLoader){
            String key = keyGenerator.generate();
            System.out.println(key);
        }
    }
}
