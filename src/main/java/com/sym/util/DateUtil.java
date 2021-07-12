package com.sym.util;

import org.joda.time.DateTimeComparator;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/*
 * <p>
 * All letters 'A' to 'Z' and 'a' to 'z' are reserved as pattern letters. The
 * following pattern letters are defined:
 * <pre>
 *  Symbol  Meaning                     Presentation      Examples
 *  ------  -------                     ------------      -------
 *   G       era                         text              AD; Anno Domini; A
 *   u       year                        year              2004; 04
 *   y       year-of-era                 year              2004; 04
 *   D       day-of-year                 number            189
 *   M/L     month-of-year               number/text       7; 07; Jul; July; J
 *   d       day-of-month                number            10
 *
 *   Q/q     quarter-of-year             number/text       3; 03; Q3; 3rd quarter
 *   Y       week-based-year             year              1996; 96
 *   w       week-of-week-based-year     number            27
 *   W       week-of-month               number            4
 *   E       day-of-week                 text              Tue; Tuesday; T
 *   e/c     localized day-of-week       number/text       2; 02; Tue; Tuesday; T
 *   F       week-of-month               number            3
 *
 *   a       am-pm-of-day                text              PM
 *   h       clock-hour-of-am-pm (1-12)  number            12
 *   K       hour-of-am-pm (0-11)        number            0
 *   k       clock-hour-of-am-pm (1-24)  number            0
 *
 *   H       hour-of-day (0-23)          number            0
 *   m       minute-of-hour              number            30
 *   s       second-of-minute            number            55
 *   S       fraction-of-second          fraction          978
 *   A       milli-of-day                number            1234
 *   n       nano-of-second              number            987654321
 *   N       nano-of-day                 number            1234000000
 *
 *   V       time-zone ID                zone-id           America/Los_Angeles; Z; -08:30
 *   z       time-zone name              zone-name         Pacific Standard Time; PST
 *   O       localized zone-offset       offset-O          GMT+8; GMT+08:00; UTC-08:00;
 *   X       zone-offset 'Z' for zero    offset-X          Z; -08; -0830; -08:30; -083015; -08:30:15;
 *   x       zone-offset                 offset-x          +0000; -08; -0830; -08:30; -083015; -08:30:15;
 *   Z       zone-offset                 offset-Z          +0000; -0800; -08:00;
 *
 *   p       pad next                    pad modifier      1
 *
 *   '       escape for text             delimiter
 *   ''      single quote                literal           '
 *   [       optional section start
 *   ]       optional section end
 *   #       reserved for future use
 *   {       reserved for future use
 *   }       reserved for future use
 * </pre>
 */
public class DateUtil {
    public static String[] MONTH = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static String[] MONTH_ALL = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    /**
     * 格式:yyyy-MM-dd HH:mm:ss
     **/
    public static final String DATE_TIME_20 = "yyyy-MM-dd HH:mm:ss";
    /**
     * 格式:yyyy-MM-dd HH:mm:ss
     **/
    public static final String DATE_TIME_10 = "yyyy-MM-dd";
    /**
     * 格式:yyyyMM
     **/
    public static final String MONTH_6 = "yyyyMM";
    /**
     * 格式:yyyyMMdd
     **/
    private static final String DATE_8 = "yyyyMMdd";

    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            return new SimpleDateFormat(pattern).format(date);
        }
        return null;
    }

    public static Date parseDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(date);
    }

    public static Date parseDate(Object date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(date.toString());
    }

    public static Date parseDate(long lo) throws ParseException {
        Date date = new Date(lo);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(date.toString());
    }


    public static String genStrWithPattern(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static boolean isSameDay(Date first, Date second) {
        return DateTimeComparator.getDateOnlyInstance().compare(first, second) == 0;
    }

    public static boolean isSame(Date source, Date target) {
        return DateTimeComparator.getInstance().compare(source, target) == 0;
    }

    public static boolean isAfter(Date source, Date target) {
        return DateTimeComparator.getInstance().compare(source, target) > 0;
    }

    public static boolean isBefore(Date source, Date target) {
        return DateTimeComparator.getInstance().compare(source, target) < 0;
    }

    public static long daysBetween(Date start, Date end) {
        return Days.daysBetween(new LocalDate(start.getTime()), new LocalDate(end.getTime())).getDays();
    }

    public static long daysBetween(long start, Date end) {
        return Days.daysBetween(new LocalDate(start), new LocalDate(end.getTime())).getDays();
    }

    public static int yearsBetween(Date start, Date end) {
        return Years.yearsBetween(new LocalDate(start.getTime()), new LocalDate(end.getTime())).getYears();
    }

    public static Date now() {
        LocalDateTime dueDateTime = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Date.from(dueDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Date now1() {
        LocalDateTime dueDateTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Date.from(dueDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Date currentDate() {
        LocalDate dueDate = new LocalDate();
        return dueDate.toDate();
    }

    public static Date plusMinutes(Date date, long minutes) {
        if (date == null) {
            return null;
        }
        LocalDateTime dueDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Date.from(dueDateTime
                .plusMinutes(minutes)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Date plusDays(Date date, long days) {
        if (date == null) {
            return null;
        }
        LocalDateTime dueDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Date.from(dueDateTime
                .plusDays(days)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Date minusDays(Date date, long days) {
        if (date == null) {
            return null;
        }
        LocalDateTime dueDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Date.from(dueDateTime
                .minusDays(days)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Date plusMonths(Date date, long months) {
        if (date == null) {
            return null;
        }
        LocalDateTime dueDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Date.from(dueDateTime
                .plusMonths(months)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Date minusMonths(Date date, long months) {
        if (date == null) {
            return null;
        }
        LocalDateTime dueDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Date.from(dueDateTime
                .minusMonths(months)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static String formaterDate(Date date) {
        StringBuilder sb = new StringBuilder();
        sb.append(MONTH[date.getMonth()]).append("-").append(date.getDate()).append("-").append(1900 + date.getYear());
        return sb.toString();

    }

    /**
     * 获取当前日期所在月的最后一天日期
     *
     * @param time
     * @return
     * @author qiuyuhua
     * 2016年9月21日 下午2:14:04
     */
    public static Date getLastDateOfMonth(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 0);//设置为1,当前日期既为本月第一天
        return cal.getTime();
    }

    public static String dateToString20(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(DATE_TIME_20).format(date);
    }

    public static String dateToString6(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(MONTH_6).format(date);
    }

    public static String dateToString8(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(DATE_8).format(date);
    }

    /**
     * 天数加减
     *
     * @param date   日期
     * @param offset 加减天数
     * @return
     * @Title: changeDay
     */
    public static Date changeDay(Date date, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR,
                (calendar.get(Calendar.DAY_OF_YEAR) + offset));
        return calendar.getTime();
    }

    /**
     * 半年内时间
     *
     * @param cteateTime
     * @param time
     * @return
     */
    public static boolean isHalfYear(Date cteateTime, long time) {
        boolean isHalfYear = false;
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_20);
            date = sdf.parse(sdf.format(minusMonths(cteateTime, 6)));
            if (time > date.getTime()) {
                isHalfYear = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isHalfYear;
    }

    /***
     * @Description 间隔天数
     * @author jialv.ye
     * @date 2018/10/15 19:26
     **/
    public static boolean isBetweenDays(Date cteateTime, long time, int day) {
        boolean isBetweenDays = false;
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_10);
            date = sdf.parse(sdf.format(changeDay(cteateTime, day)));
            if (time > date.getTime()) {
                isBetweenDays = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isBetweenDays;
    }

    /***
     * @Description 间隔天数
     * @author jialv.ye
     * @date 2018/10/15 19:26
     **/
    public static boolean isBetweenDays(Date cteateTime, Date time, int day) {
        boolean isBetweenDays = false;
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_10);
            date = sdf.parse(sdf.format(changeDay(cteateTime, day)));
            if (time.getTime() > date.getTime()) {
                isBetweenDays = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isBetweenDays;
    }

    /**
     * @Description 获取相隔的天数
     **/
    public static int getBetweenDays(long startTime, Date endTime) {

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(new Date(startTime));

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endTime);
        int startDay = startCal.get(Calendar.DAY_OF_YEAR);
        int endDay = endCal.get(Calendar.DAY_OF_YEAR);

        int startYear = startCal.get(Calendar.YEAR);
        int endYear = endCal.get(Calendar.YEAR);
        if (startYear != endYear) {
            int timeDistance = 0;
            for (int i = startYear; i < endYear; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    timeDistance += 366;
                } else {
                    timeDistance += 365;
                }
            }
            return timeDistance + (endDay - startDay);
        } else {
            return endDay - startDay;
        }
    }

    /**
     * 获取年龄
     *
     * @param birthDay
     * @return
     */
    public static int getAge(Date birthDay) {
        Calendar calendar = Calendar.getInstance();
        if (calendar.before(birthDay)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH);
        int dayOfMonthNow = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(birthDay);
        int yearBirth = calendar.get(Calendar.YEAR);
        int monthBirth = calendar.get(Calendar.MONTH);
        int dayOfMonthBirth = calendar.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     * 判断是否为今日的时间
     *
     * @return
     */
    public static boolean isToday(Date date) {
        return dateToString8(now()).equals(dateToString8(date));
    }


    /**
     * 获取当前时间所在天的最后一秒
     *
     * @param time
     * @return
     */
    public static Date getLastTimeOfDay(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 获取当前时间所在天的最后一秒
     *
     * @param time
     * @return
     */
    public static Date getFirstTimeOfDay(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * format localdate
     *
     * @param localDate
     * @param format
     * @return
     */
    public static String formatDate(java.time.LocalDate localDate, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDate.format(formatter);
    }

    /**
     * localdate转date
     *
     * @param date
     * @return
     */
    public static Date localDateToDate(java.time.LocalDate date) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = date.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 获取多少分钟之前的时间或之后
     * @param minutes
     * @return
     */
    public static Date getCurrentTime(Integer minutes) {
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, minutes);
        return beforeTime.getTime();
    }

    /**
     * 输出 Month 15,2019 格式
     */
    public static String formateDateToEnglish(Date date) {
        if (date == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        sb.append(MONTH[cal.get(Calendar.MONTH)]).append(" ").append(cal.get(Calendar.DAY_OF_MONTH)).append(", ").append(cal.get(Calendar.YEAR));
        return sb.toString();
    }

}
