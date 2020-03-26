package com.sym;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Created by 沈燕明 on 2018/12/8.
 */
public class YourTest {

    @Test
    public void testOne() {
        StringBuilder sb = new StringBuilder("123");
        String s = sb.replace(sb.length() - 1, sb.length()-1, "4").toString();
        System.out.println(s);
    }

    @Test
    public void testTwo() {
        String s = LocalDate.now().toString();
        System.out.println(s);
    }

    @Test
    public void testThree() {
        LocalDate date = LocalDate.now();
        System.out.println(date.isBefore(LocalDate.now()));
    }

    @Test
    public void testFour() {
        String dicSetup = "1:981*2:42*3:566612*4:104*5:3040*6:340*7:56221*8:3405*9:4023*10:2467*11:5320*12:5*13:972*14:15*15:52*16:10976*17:97*18:3782921*19:342*20:78862*22:89*38:692*50:3*90:309*234:33*3245:348";
        // 单独用*号会报错, 所以需要加上[]包裹
        String[] split = dicSetup.split("[*]");
        System.out.println(Arrays.toString(split));
    }

    @Test
    public void testFive() {

    }
    


}
