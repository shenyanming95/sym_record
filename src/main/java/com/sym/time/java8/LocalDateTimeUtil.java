package com.sym.time.java8;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 常用时间计算的工具类
 * <p>
 * {@link java.time.LocalDate}
 * {@link java.time.LocalTime}
 * {@link java.time.LocalDateTime}
 * {@link java.time.Instant}
 *
 * @author shenyanming
 * Created on 2020/7/15 09:44
 */
public class LocalDateTimeUtil {

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

    /**
     * 获取本周的周一0:00
     *
     * @return 时间点
     */
    public static LocalDateTime getStartTimeOfWeek() {
        return getStartTimeOfWeek(LocalDate.now());
    }

    /**
     * 获取指定日期的周一0:00
     *
     * @param date 指定日期
     * @return 时间点
     */
    public static LocalDateTime getStartTimeOfWeek(LocalDate date) {
        // 当前日期位于一周的周几, 例如周一对应1, 周日对应7
        int value = date.getDayOfWeek().getValue();
        // 减去相应的日期, 就等于周一
        LocalDate startDayOfWeek = date.minusDays(value - 1);
        return startDayOfWeek.atStartOfDay();
    }

    /**
     * 获取本周的周日23:59
     *
     * @return 时间点
     */
    public static LocalDateTime getEndTimeOfWeek() {
        return getEndTimeOfWeek(LocalDate.now());
    }

    /**
     * 获取指定日期的周日 23:59:59
     *
     * @param date 指定日期
     * @return 时间点
     */
    public static LocalDateTime getEndTimeOfWeek(LocalDate date) {
        LocalDateTime start = getStartTimeOfWeek(date);
        return start.plusDays(7).minusSeconds(1);
    }

    /**
     * 获取指定日期的当前周, 在当前月的周次
     *
     * @param date 指定日期
     * @return 周次, 1~5
     */
    public static int getWeekNumOfMonth(LocalDate date) {
        // 先计算出本周的周一
        LocalDateTime startTimeOfWeek = getStartTimeOfWeek(date);
        // 再计算出周一位于当前月的天次
        int dayOfMonth = startTimeOfWeek.getDayOfMonth();
        // 除以7向下取整后加1
        return dayOfMonth / 7 + 1;
    }
}
