package com.sym;

import com.sym.util.JsonResult;
import org.junit.Test;

import java.beans.PropertyChangeSupport;
import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 沈燕明 on 2018/12/8.
 */
public class YourTest {

    @Test
    public void testOne() {
        StringBuilder sb = new StringBuilder("123");
        String s = sb.replace(sb.length() - 1, sb.length() - 1, "4").toString();
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
        PropertyChangeSupport support = new PropertyChangeSupport(JsonResult.class);
        support.addPropertyChangeListener(System.out::println);

        support.firePropertyChange("success12", false, true);
        support.fireIndexedPropertyChange("success", 1, 12, 456);
    }

    @Test
    public void test0001(){
        String path = "F:\\视频缓冲区\\汪文君 - Java多线程编程实战\\第三阶段\\";
        File[] files = new File(path).listFiles(f -> !f.isDirectory() && !f.getName().endsWith(".zip"));
        List<File> list = Arrays.asList(files);
//        Comparator<File> comparator = Comparator.comparing(o -> substring(o.getName()));
//        Comparator<File> comparing = comparator.thenComparing(o -> substring2(o.getName()));
        AtomicInteger index = new AtomicInteger(1);
//        list.sort(comparing);
        list.forEach(f -> {
            String name = f.getName();
            int i = name.indexOf(" ");
            name = name.substring(i + 1);
            String newName = intToString(index.getAndIncrement()) + "丨" + name;
            newName = newName.replace("_", "");
            System.out.println(f.renameTo(new File(path + newName.trim())));
        });
    }

    private String intToString(int i){
        if(i < 10){
            return "0" + i;
        }
        return String.valueOf(i);
    }

    private Integer substring(String name){
        int i = name.indexOf("-");
        return Integer.valueOf(name.substring(0, i));
    }

    private Integer substring2(String name){
        int i1 = name.indexOf("-");
        int i2 = name.indexOf(" ");
        return Integer.valueOf(name.substring(i1 + 1, i2));
    }

    @Test
    public void test0002(){
        String path = "F:\\视频缓冲区\\汪文君 - Java多线程编程实战\\第一阶段\\";
        File dir = new File(path);
        File[] listFiles = dir.listFiles(f -> !f.getName().endsWith(".rar"));
        AtomicInteger index = new AtomicInteger(1);
        for (File file : listFiles) {
            String name = file.getName();
            int i = name.lastIndexOf("丨");
            name = name.substring(i + 1).trim();
            name = intToString(index.getAndIncrement()) + "丨" + name;
            name = name.replace("_[itjc8.com]", "");

            //file.renameTo(new File(path + name));
        }
    }
}
