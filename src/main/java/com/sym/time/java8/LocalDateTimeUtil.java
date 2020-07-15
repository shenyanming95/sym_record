package com.sym.time.java8;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * {@link java.time.LocalDate}
 * {@link java.time.LocalTime}
 * {@link java.time.LocalDateTime}
 * {@link java.time.Instant}
 *
 * @author shenyanming
 * Created on 2020/7/15 09:44
 */
public class LocalDateTimeUtil {

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(toMillisecond(now));
        System.out.println(toDate(now));
    }

    /**
     * 转换毫秒数
     *
     * @param dateTime 时间点
     * @return 对应毫秒数
     */
    public static long toMillisecond(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 转换秒数
     *
     * @param dateTime 时间点
     * @return 对应秒数
     */
    public static long toSecond(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * LocalDateTime 转 Date
     *
     * @param dateTime 时间点
     * @return 对应的Date对象
     */
    public static Date toDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date 转 LocalDateTime
     *
     * @param date 时间
     * @return 时间点
     */
    public static LocalDateTime fromDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
