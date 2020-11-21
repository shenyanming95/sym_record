package com.sym.quartz.jobDetail;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 执行JOb
 *
 * Created by 沈燕明 on 2019/11/6 21:58.
 */
public class SymScheduler {
    public static void main(String[] args) throws SchedulerException {
        // jobDataMap, 默认情况下每次调用都会创建一个新的JobDataMap
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("times",0);
        dataMap.put("message","Job实例的message");

        // job实例
        JobDetail jobDetail = JobBuilder.newJob(SymJob.class)
                .setJobData(dataMap)
                .withIdentity("自定义Job名称","自定义Job分组")
                .build();
        System.out.println("名称="+jobDetail.getKey().getName());//不填默认UUID
        System.out.println("组别="+jobDetail.getKey().getGroup());//不填默认DEFAULT

        // 触发器
        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .startNow()
                .usingJobData("message","触发器的message")
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5)).build();

        // 启动调取器
        Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
        defaultScheduler.scheduleJob(jobDetail, trigger);
        defaultScheduler.start();
    }
}
