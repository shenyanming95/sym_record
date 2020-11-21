package com.sym.quartz.listener;


import com.sym.quartz.listener.jobListener.SymJobListener;
import com.sym.quartz.listener.jobListener.SymScheduleListener;
import com.sym.quartz.listener.jobListener.SymTriggerListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.impl.matchers.NameMatcher;

import java.util.Date;

/**
 * Created by 沈燕明 on 2019/11/9 20:17.
 */
public class SymSchedule {
    public static void main(String[] args) throws SchedulerException {
        Date now = new Date();
        /*
         * 创建JobDetail
         */
        JobDetail jobOne = JobBuilder.newJob(SymJob.class).withIdentity("二号", "用户组").build();
        JobDetail jobTwo = JobBuilder.newJob(SymJob.class).withIdentity("一号", "用户组").build();

        /*
         * 简单触发器, 就执行一次
         */
        SimpleTrigger triggerOne = TriggerBuilder.newTrigger().withSchedule(SimpleScheduleBuilder.repeatHourlyForTotalCount(1)).build();
        SimpleTrigger triggerTwo = TriggerBuilder.newTrigger().withIdentity("触发器二号").startAt(new Date(now.getTime()+5000)).withSchedule(SimpleScheduleBuilder.repeatHourlyForTotalCount(1)).build();

        /*
         * 调度器
         */
        Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
        // 貌似一个触发器只能执行一个job, 这个等遇到了追源码去...
        defaultScheduler.scheduleJob(jobOne,triggerOne);
        defaultScheduler.scheduleJob(jobTwo,triggerTwo);

        //注册Job监听器, 它需要一个Matcher接口实现类, 这边我们让改监听器, 对所有job生效
        defaultScheduler.getListenerManager().addJobListener(new SymJobListener(), EverythingMatcher.allJobs());
        // defaultScheduler.getListenerManager().addJobListener(new SymJobListener(), OrMatcher.or(NameMatcher.nameEquals("一号"), NameMatcher.nameEquals("二号")));// 还可以做复杂的匹配

        //注册trigger监听器
        defaultScheduler.getListenerManager().addTriggerListener(new SymTriggerListener(), NameMatcher.nameEquals("触发器二号"));

        //注册调度容器监听器
        defaultScheduler.getListenerManager().addSchedulerListener(new SymScheduleListener());
        defaultScheduler.start();
    }
}
