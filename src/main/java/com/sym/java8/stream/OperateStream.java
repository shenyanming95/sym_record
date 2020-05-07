package com.sym.java8.stream;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 操作Stream，操作流的API都会返回一个新流
 *
 * @Auther: shenym
 * @Date: 2018-12-12 16:34
 */
public class OperateStream {

    private Stream<Integer> initStream;

    @Before
    public void init() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 11, 23, 45, 343, 11, 454, 78, 3, 8));
        initStream = list.stream();
    }

    /**
     * 过滤
     */
    @Test
    public void testFilter() {

        // 过滤元素,可以使用lambda表达式,每个元素都会执行lambda体
        // 返回true的保留,返回false的排除,filter()方法返回一个新Stream
        Stream stream = initStream.filter((a) -> {
            if (a > 100) return true;
            else return false;
        });

        print(stream);

    }


    /**
     * 去重
     */
    @Test
    public void testDistinct() {

        // 对原流中的数据进行去重
        Stream stream = initStream.distinct();

        print(stream);

    }


    /**
     * 截断流，限制流的大小
     */
    @Test
    public void testLimit() {

        // 截断流，让流中的大小不超过指定的值
        Stream stream = initStream.limit(2);

        print(stream);

    }


    /**
     * 跳过元素
     */
    @Test
    public void testSkip() {

        // 跳过原stream的前6个元素,返回剩下的元素作为新的stream
        Stream stream = initStream.skip(6);

        print(stream);

    }


    /**
     * 将原流中的元素转为新类型的元素
     */
    @Test
    public void testMap() {

        // 将原stream中整型的元素转换成字符串,map()返回一个新的stream对象
        Stream<String> strStream = initStream.map((a) -> {
            String s = String.valueOf(a);
            return s;
        });

        print(strStream);

    }


    /**
     * 将原流中的每一个元素转换成一个新的流,然后将所有流拼成一个流
     */
    @Test
    public void testFlatMap() {

        Stream<Integer> stream = initStream.flatMap((a) -> {
            return Stream.of(a);
        });
        print(stream);
    }


    /**
     * 将原流中的元素进行自然排序,在重新返回一个排序好的新stream,
     * 默认是以升序的方式排序,而且元素必须实现java.lang.Comparable接口
     */
    @Test
    public void testSort() {

        Stream stream = initStream.sorted();
        print(stream);

    }


    /**
     * 传入一个Comparator接口,让原流中的元素按照Comparator进行排序
     */
    @Test
    public void testSortTwo() {

        Stream<String> stringStream = Stream.of("good", "main", "you", "stupid");
        Stream stream = stringStream.sorted((a, b) -> {
            Character c1 = a.charAt(1);
            Character c2 = b.charAt(1);
            return c2.compareTo(c1);
        });

        print(stream);

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
