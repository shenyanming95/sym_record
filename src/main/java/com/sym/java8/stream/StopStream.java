package com.sym.java8.stream;

import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 中止stream,中止流中的API都会关闭stream,意味着这个stream不能再被操作了
 *
 * @Auther: shenym
 * @Date: 2018-12-13 14:21
 */
public class StopStream {

    /**
     * allMatch是流中所有元素都要匹配
     * anyMatch是流中至少有一个元素要匹配
     * noneMatch是流中没有任何一个元素可以匹配
     */
    @Test
    public void testMatch() {

        /* 流中的所有元素是否都大于1 */
        Stream<Integer> oneStream = Stream.of(5, 65, 23, 88, 11);
        boolean oneFlag = oneStream.allMatch((a) -> {
            if (a > 1) return true;
            else return false;
        });

        /* 流中是否有元素等于12 */
        Stream<Integer> twoStream = Stream.of(12, 23, 45, 67, 14);
        boolean twoFlag = twoStream.anyMatch((a) -> {
            if (a == 12) return true;
            else return false;
        });

        /* 流中是否不包含100 */
        Stream<Integer> threeStream = Stream.of(10, 20, 30, 40, 50);
        boolean threeFlag = threeStream.noneMatch((a) -> {
            if (a == 100) return true;
            else return false;
        });

        System.out.println(oneFlag);
        System.out.println(twoFlag);
        System.out.println(threeFlag);

    }


    /**
     * 返回流中的元素
     */
    @Test
    public void testFind() {


        // 返回流中的第一个元素
        Stream<String> firstStream = Stream.of("well", "good", "right", "fine");
        Optional<String> first = firstStream.findFirst();
        System.out.println(first.get());

        // 返回流中的任意元素
        Stream<Integer> anyStream = Stream.of(12, 32, 42, 52, 62, 72, 82);
        Optional<Integer> any = anyStream.findAny();
        System.out.println(any.get());

    }


    /**
     * 统计流中元素的总量
     */
    @Test
    public void testCount() {

        Stream initStream = Stream.of(11, 22, 33, 44, 55, 66);
        long count = initStream.count();

        System.out.println(count);

    }


    /**
     * 获取流中的最大值和最小值
     */
    @Test
    public void testMax() {

        // 获取流中的最大值
        Stream<Integer> maxStream = Stream.of(234, 565, 3242, 36, 56, 324, 2365);
        Optional<Integer> max = maxStream.max((a, b) -> {
            int c = a.compareTo(b);
            return c;
        });
        System.out.println(max.get());

        // 获取流中的最小值
        Stream<Integer> minStream = Stream.of(234, 565, 3242, 36, 56, 324, 2365);
        Optional<Integer> min = minStream.min((a, b) -> {
            int c = a.compareTo(b);
            return c;
        });
        System.out.println(min.get());
    }


    /**
     * 将流转换为各种格式的数据
     */
    @Test
    public void testCollect(){

        // 转换为List集合
        Stream<Integer> stream1 = Stream.of(234, 565, 3242, 36, 56, 324, 2365);
        List<Integer> collect1 = stream1.collect(Collectors.toList());

        // 转换为set集合
        Stream<Integer> stream2 = Stream.of(234, 565, 3242, 36, 56, 324, 2365);
        Set<Integer> collect2 = stream2.collect(Collectors.toSet());

    }



    /**
     * 打印流中的数据
     *
     * @param stream
     */
    private void print(Stream stream) {
        stream.forEach((a) -> {
            System.out.print(a + " ");
        });
    }
}
