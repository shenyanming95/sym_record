package com.sym.quartz.simple;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 在 Quartz 中, 有3个大组件, 分别是：Job实例(JobDetail)、触发器(trigger)、调度容器(scheduler)
 * JobDetail 定义具体的定时任务
 * trigger 在指定时间点触发 JobDetail 的执行
 * scheduler将 JobDetail 和 trigger 关联在一起, 负责基于 trigger 设定的时间来执行 JobDetail.
 * <p>
 * 定义这些实例需要用到许多静态方法，可以使用静态导入的方式，将对应类导入进来，类似：
 * import static org.quartz.TriggerBuilder.newTrigger( 但不要过度依赖于静态导入，因为这样不便于代码阅读 )
 */
public class SimpleScheduler {

    public static void main(String[] args) throws Exception {

        // 用 schedulerFactory 获取 Scheduler 实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 由于 scheduler 每次执行 job，都会实例化一个新的对象，之前那个对象的引用会被断掉(结果就是被垃圾回收)
        // 所以当在 job 中需要使用参数时，不能直接在 job 中初始化( 因为每次执行都会从原始数据开始 )，这时候就需要
        // JobDataMap来传递参数，它的用法类似 map
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("id", 1);
        dataMap.put("name", "张三");

        // 定义任务实例jobDetail
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class) // 定义 jobDetail 实例时，需要一个实现Job接口的类
                .withIdentity("test-job", "sym-job") // 给 jobDetail 实例指定身份，叫什么，属于哪个组
                .setJobData(dataMap) // 设置参数
                .build();

        // 定义触发器trigger
        Trigger trigger = newTrigger().withIdentity("test-trigger", "sym-job") // 给trigger设置身份
                // 每10秒执行1次, 并且重复执行2次, 启动时执行的那次不会算, 所以一共执行3次
                .withSchedule(simpleSchedule().withIntervalInSeconds(5).withRepeatCount(2))
                //.startAt(new Date()) //开始执行时间, 从这个时间开始执行
                //.endAt(new Date()) //结束执行时间, 到这个点结束执行
                .build();

        // 将jobDetail和trigger同时交给调度容器执行
        scheduler.scheduleJob(jobDetail, trigger);


        // 开启调度容器，这一瞬间就开始运行了
        scheduler.start();


        // 关闭调度容器，一旦关闭，如果需要重新执行，就需要重新实例化
        // scheduler.shutdown();

    }
}
