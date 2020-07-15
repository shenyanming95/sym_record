package com.sym.util;

/**
 * 程序运行时间监控类
 *
 * @author shenyanming
 * @date 2019/6/25 9:30
 */
public class TimeWatch {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 记录程序运行起始点
     *
     * @return 当前时间戳
     */
    public static long start() {
        long l = System.currentTimeMillis();
        threadLocal.set(l);
        return l;
    }

    /**
     * 记录程序运行结束点
     *
     * @return 耗时，单位ms
     */
    public static long end() {
        return end(false);
    }

    /**
     * 记录程序运行结束点
     *
     * @param print 是否需要打印
     * @return 耗时，单位ms
     */
    public static long end(boolean print) {
        long endPoint = System.currentTimeMillis();
        Long startPoint;
        if ((startPoint = threadLocal.get()) == null) {
            throw new IllegalArgumentException("未设置一个监控起始时间点");
        }
        long result = endPoint - startPoint;
        if (print) {
            System.out.println("耗时：" + result);
        }
        threadLocal.remove();
        return result;
    }
}
