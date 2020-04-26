package com.sym.clone.array;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author shenym
 * @date 2019/9/3
 */
public class ArrayCloneTest {


    @Test
    public void testOne() {
        int[] src = new int[]{1, 2, 3, 4, 5};
        int[] dest = new int[src.length];
        print(dest);
        /*
         * System.arraycopy()高效的数据拷贝，共有5个参数，含义依次是：
         * src：原数组
         * srcPos：从原数组的指定下标开始拷贝
         * dest：目标数组
         * destPos：指定拷贝到目标数组的起始下标
         * length：要拷贝的数组元素的数量
         */
        System.arraycopy(src, 0, dest, 0, src.length);
        print(dest);
    }


    private void print(int[] array) {
        int len = array.length;
        AtomicInteger idx = new AtomicInteger(0);
        System.out.print("[");
        Arrays.stream(array).forEach(a -> {
            idx.incrementAndGet();
            System.out.print(a + (idx.intValue() == len ? "" : ","));

        });
        System.out.println("]");
    }

}
