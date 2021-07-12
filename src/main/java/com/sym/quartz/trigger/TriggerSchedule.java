package com.sym.quartz.trigger;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * {@link SimpleTrigger}的属性有：开始执行时间、结束时间、重复次数和重复的时间间隔
 * 1、重复时间的间隔属性值必须为大于0的正整数，以毫秒作为单位，当重复时间间隔为0时，意味着Trigger同时出发进行
 * 2、如果有指定时间属性值，则结束时间优先于重复次数
 *
 * Created by 沈燕明 on 2019/11/9 12:13.
 */
public class TriggerSchedule {
    public static void main(String[] args) throws SchedulerException {
        Date now = new Date();


        // 待执行的两个Job实例
        JobDetail jobDetailOne = JobBuilder.newJob(TriggerJob.class).withIdentity("疾风剑豪", "刺客").withDescription("面对疾风吧").build();
        JobDetail jobDetailTwo = JobBuilder.newJob(TriggerJob.class).withIdentity("灵魂烈焰", "法师").withDescription("面对疾风吧").build();


        /*
         * 定义个SimpleTrigger, 它只能做一些简单的重复操作
         */
        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
                .startAt(new Date(now.getTime() + 1000))
                .endAt(new Date(now.getTime() + 5000))
                // 这里SimpleScheduleBuilder的意思：每1s重复执行, 但是又给它限制只重复1次, 算上启动时执行的一次, 这个配置实际上只会执行Job两次
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withRepeatCount(1)).build();

        /*
         * 定义CronTrigger, 它可以执行cron表达式，
         * 与spring不同的是Quartz支持的cron表达式是标准的7位（算上最后一位年份）
         */
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))
                .endAt(new Date(now.getTime()+10000))
                .build();

        // 创建调度器, 执行job
        Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
        defaultScheduler.scheduleJob(jobDetailOne,simpleTrigger);
        defaultScheduler.scheduleJob(jobDetailTwo,cronTrigger);
        defaultScheduler.start();
    }
}
