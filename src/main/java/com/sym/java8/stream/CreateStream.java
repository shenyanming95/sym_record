package com.sym.java8.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 创建流stream
 *
 * @Auther: shenym
 * @Date: 2018-12-12 16:14
 */
public class CreateStream {

    /**
     * 通过Collection接口的stream()可以获取顺序流
     * 通过Collection接口的parallelStream()可以获取并行流
     */
    @Test
    public void testOne() {

        List<String> list = new ArrayList<>(Arrays.asList("aa", "ab", "bb", "bc"));
        Stream<String> stream = list.stream();
        Stream<String> parallelStream = list.parallelStream();

        stream.count();
        parallelStream.count();

    }


    /**
     * 通过Arrays的stream()方法可以从数组中获取流
     */
    @Test
    public void testTwo() {

        int[] intarray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        IntStream intStream = Arrays.stream(intarray);

        intStream.count();
    }


    /**
     * 通过stream.of()方法获取流,它接收一个不定长度参数
     */
    @Test
    public void testThree(){

        Stream<String> stringStream = Stream.of("", "", "");

        stringStream.count();
    }


}
