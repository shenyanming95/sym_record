package com.sym.quartz.listener;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.impl.matchers.NameMatcher;
import org.quartz.listeners.SchedulerListenerSupport;

import java.util.Date;

/**
 * Created by 沈燕明 on 2019/11/9 20:17.
 */
public class ListenerSchedule {
    public static void main(String[] args) throws SchedulerException {
        Date now = new Date();
        /*
         * 创建JobDetail
         */
        JobDetail jobOne = JobBuilder.newJob(ListenerJob.class).withIdentity("二号", "用户组").build();
        JobDetail jobTwo = JobBuilder.newJob(ListenerJob.class).withIdentity("一号", "用户组").build();

        /*
         * 简单触发器, 就执行一次
         */
        SimpleTrigger triggerOne = TriggerBuilder.newTrigger().withSchedule(SimpleScheduleBuilder.repeatHourlyForTotalCount(1)).build();
        SimpleTrigger triggerTwo = TriggerBuilder.newTrigger().withIdentity("触发器二号").startAt(new Date(now.getTime() + 5000)).withSchedule(SimpleScheduleBuilder.repeatHourlyForTotalCount(1)).build();

        /*
         * 调度器
         */
        Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
        // 貌似一个触发器只能执行一个job, 这个等遇到了追源码去...
        defaultScheduler.scheduleJob(jobOne, triggerOne);
        defaultScheduler.scheduleJob(jobTwo, triggerTwo);

        //注册Job监听器, 它需要一个Matcher接口实现类, 这边我们让改监听器, 对所有job生效
        defaultScheduler.getListenerManager().addJobListener(new SymJobListener(), EverythingMatcher.allJobs());
        // defaultScheduler.getListenerManager().addJobListener(new SymJobListener(), OrMatcher.or(NameMatcher.nameEquals("一号"), NameMatcher.nameEquals("二号")));// 还可以做复杂的匹配

        //注册trigger监听器
        defaultScheduler.getListenerManager().addTriggerListener(new SymTriggerListener(), NameMatcher.nameEquals("触发器二号"));

        //注册调度容器监听器
        defaultScheduler.getListenerManager().addSchedulerListener(new SymScheduleListener());
        defaultScheduler.start();
    }

    /**
     * 实现{@link JobListener}就可以在job执行期间, 监听它的事件
     */
    public static class SymJobListener implements JobListener {

        /**
         * 为该JobListener取一个名字
         */
        @Override
        public String getName() {
            return "sym-jobListener";
        }

        /**
         * job执行前, 如果{@link TriggerListener}允许执行, 则调用此方法
         * 它与{@link SymJobListener#jobExecutionVetoed}方法是互斥的, 只有一个会执行
         */
        @Override
        public void jobToBeExecuted(JobExecutionContext context) {
            System.out.println("Job即将执行," + getInfo(context));
        }


        /**
         * job执行前, 如果{@link TriggerListener}否决此Job执行, 则调用此方法
         * 它与{@link SymJobListener#jobExecutionVetoed}方法是互斥的, 只有一个会执行
         */
        @Override
        public void jobExecutionVetoed(JobExecutionContext context) {
            System.out.println("job被否决执行," + this.getInfo(context));
        }

        /**
         * job执行后
         */
        @Override
        public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
            System.out.println("job执行后," + this.getInfo(context));
            System.out.println();
            // 如果job执行期间发生了异常，则异常在jobException
        }

        private String getInfo(JobExecutionContext context) {
            JobDetail jobDetail = context.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            return "Job信息：name=" + jobKey.getName() + ",group=" + jobKey.getGroup();
        }
    }

    public static class SymScheduleListener extends SchedulerListenerSupport {
        @Override
        public void schedulerStarted() {
            System.out.println("调度容器启动l。。");
        }
    }

    public static class SymTriggerListener implements TriggerListener {

        /**
         * 定义并返回监听器的名字
         */
        @Override
        public String getName() {
            return "trigger监听器";
        }

        /**
         * 触发器被{@link org.quartz.Scheduler}触发, 在执行{@link org.quartz.Job#execute(JobExecutionContext)}方法之前
         * 会调用此方法
         */
        @Override
        public void triggerFired(Trigger trigger, JobExecutionContext context) {
            System.out.println("触发器[" + trigger.getKey() + "]执行了..");
        }

        /**
         * 如果此方法返回true，则当前Job不会执行, 意味着它被否决了
         */
        @Override
        public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
            JobKey key = context.getJobDetail().getKey();
            return key.getName().equals("一号");
        }

        /**
         * trigger错过触发时, 由Schedule调用此方法
         */
        @Override
        public void triggerMisfired(Trigger trigger) {

        }

        /**
         * Trigger 被触发并且完成了 Job 的执行时，Scheduler 调用这个方法,
         * 如果一个job被否决了, 这个方法也不会执行了
         */
        @Override
        public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
            System.out.println("触发器[" + trigger.getKey() + "]执行完..");
        }
    }
}
