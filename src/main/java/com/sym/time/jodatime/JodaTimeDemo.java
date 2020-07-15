package com.sym.time.jodatime;

import com.sym.time.TimePatternConstant;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 使用 JodaTime 工具类操作时间。
 *
 * JodaTime中的时间类都是不可变的类（final）
 * Instant - 不可变的类，用来表示时间轴上一个瞬时的点
 *
 * Created by 沈燕明 on 2019/6/25.
 */
public class JodaTimeDemo {

    /**
     * Instant用来获取一个时间戳
     */
    @Test
    public void testInstant(){
        Instant instant = Instant.now();
        System.out.println(instant.getMillis());

        Instant parseInstant = Instant.parse("2019-01-01");
        System.out.println(parseInstant.getMillis());
    }

    @Test
    public void testDateTime(){
        // 获取系统当前的时间
        DateTime now = new DateTime();
        System.out.println("获取当前时间："+now.toString(TimePatternConstant.YYYY_MM_SS_HH_MM_SS));

        // 指定生产一个时间
        DateTime time = new DateTime(2018,12,4,12,15,13);
        System.out.println("指定一个时间生产："+time.toString(TimePatternConstant.YYYY_MM_SS_HH_MM_SS));

        // 修改指定时间的年份
        DateTime time_2 = time.withYear(1995);
        System.out.println("修改时间的年份："+time_2.toString(TimePatternConstant.YYYY_MM_SS_HH_MM_SS));

        // 修改指定时间的月份
        DateTime time_3 = time.withMonthOfYear(10);
        System.out.println("修改时间的月份："+time_3.toString(TimePatternConstant.YYYY_MM_SS_HH_MM_SS));

        // 修改指定时间的日期
        // DateTime time_4 = time.withDayOfWeek(1);// 修改指定日期成当周的某个星期，取值1~7，表示周一到周日
        // DateTime time_4 = time.withDayOfYear(368); // 修改指定日期成当年的某一天，取值1~365
        DateTime time_4 = time.withDayOfMonth(5); // 修改指定日期为当月的某一天，取值1~31
        System.out.println("修改时间的日期："+time_4.toString(TimePatternConstant.YYYY_MM_SS_HH_MM_SS));

        // 指定时间的年数加1
        DateTime dateTime_5 = time.plusYears(1);
        System.out.println("增加时间的年数："+dateTime_5.toString(TimePatternConstant.YYYY_MM_SS_HH_MM_SS));

        // 指定时间的月数加1
        DateTime dateTime_6 = time.plusMonths(1);
        System.out.println("增加时间的月数："+dateTime_6.toString(TimePatternConstant.YYYY_MM_SS_HH_MM_SS));

        // 指定时间的分钟减10
        DateTime dateTime_7 = time.minusHours(2);
        System.out.println("减少时间的小时："+dateTime_7.toString(TimePatternConstant.YYYY_MM_SS_HH_MM_SS));
    }

    @Test
    public void testOne() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("2019-06-25");
        System.out.println(new DateTime(date).toString());

    }

    /**
     * java.util.Date转换为DateTime
     */
    @Test
    public void testTwo(){
        Date date = new Date();
        System.out.println(new DateTime(date).toString("yyyy年MM月dd日 HH:mm:ss"));
    }
}
