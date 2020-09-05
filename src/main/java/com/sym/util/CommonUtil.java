package com.sym.util;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author shenyanming
 * Created on 2020/9/2 10:53
 */
public class CommonUtil {

    /**
     * int[] 转 Integer[]
     *
     * @param array 整型数组
     * @return 包装类数组
     */
    public static Integer[] convert(int[] array) {
        if (Objects.isNull(array)) return null;
        return Arrays.stream(array).boxed().toArray(Integer[]::new);
    }

    /**
     * Integer[] 转 int[]
     *
     * @param array 包装类数组
     * @return 整型数组
     */
    public static int[] convert(Integer[] array) {
        if (Objects.isNull(array)) return null;
        return Arrays.stream(array).mapToInt(Integer::intValue).toArray();
    }
}
