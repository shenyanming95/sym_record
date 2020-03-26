package com.sym.time.java8;

import com.sym.time.TimePatternConstant;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * Java8提供的时间工具类
 *
 * Created by 沈燕明 on 2019/6/25.
 */
public class Java8TimeApiDemo {

    /**
     * 操作日期的LocalDate
     */
    @Test
    public void testLocalDate(){
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.toString());

        LocalDate localDate1 = LocalDate.of(2019,1,1);
        System.out.println(localDate1.plusDays(1).toString());

        LocalDate localDate2 = LocalDate.parse("2019-11-01");
        System.out.println(localDate2.toString());
    }

    /**
     * 操作时间点的LocalDateTime
     */
    @Test
    public void testLocalDateTime(){

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TimePatternConstant.YYYY_MM_SS_HH_MM_SS);

        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.toString());
        System.out.println(dateTimeFormatter.format(now));

        LocalDateTime localDateTime = LocalDateTime.parse("2019-11-11 10:00:15",dateTimeFormatter);
        System.out.println(localDateTime.toString());
        System.out.println(dateTimeFormatter.format(localDateTime));
        TemporalAccessor parse = dateTimeFormatter.parse("2019-11-11 10:00:15");
        LocalDate.from(parse);

    }
}
