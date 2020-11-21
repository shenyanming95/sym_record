package com.sym.quartz.simple;

import lombok.Data;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@link Job}接口是用来处理具体业务的接口, 我们每创建一个定时任务, 就需要实现这个接口;
 * {@link Job}接口只有一个execute方法, 方法中有个参数 JobExecutionContext 是Job在执行时的全局环境,
 * 可以在这边取到 调度容器、触发器、执行任务、执行参数... 等等
 */
@DisallowConcurrentExecution
@Data
public class SimpleJob implements Job {

    /*
     * 在job类中，为JobDataMap中存储的数据的key增加set方法，就不用显式从jobDataMap中获取数据,quartz默认在job
     * 实例化时自动调用这些方法。
     */
    private int id;
    private String name;

    public void execute(JobExecutionContext Context) {

        // 当前执行时间
        Date fireTime = Context.getFireTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = dateFormat.format(fireTime);

        // 从JobExecutionContext中获取JobDataMap
        JobDataMap map = Context.getJobDetail().getJobDataMap();


        // 如果不在Job类中自定义成员属性和相应的setter方法，则需要显示地从JobDataMap取值
        // String name = String.valueOf(map.get("name"));

        // 如果在Job类中定义了变量名和setter方法，则直接使用这些变量即可
        System.out.println("自定义job执行了...." + now + "...[参数]：id=" + id + ",name=" + name);
    }



}
