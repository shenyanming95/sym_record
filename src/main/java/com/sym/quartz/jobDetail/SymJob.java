package com.sym.quartz.jobDetail;

import lombok.Setter;
import org.quartz.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Job实例在Quartz的生命周期：
 * 每次调度器{@link Scheduler}执行job时, 都会在execute()执行前创建一个新的Job实例; 当调度完成后, 关联的Job实例会被释放掉, 然后被垃圾回收
 *
 * {@link PersistJobDataAfterExecution}注解用来区分无状态Job和有状态Job：有状态Job在整个调用期间, 会持有状态信息, 存储在JobDataMap中
 *                                                                      无状态Job在整个调用期间, JobDataMap都是新的, 每次调用都会创建一个新的对象
 * Created by 沈燕明 on 2019/11/6 21:56.
 */
@PersistJobDataAfterExecution
public class SymJob implements Job {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SymJob(){
        System.out.println("创建了一个新的Job实例==SymJob");
    }


    /*
     * 通过在Job实现类内定义成员变量, 并指定一个setter方法, Quartz会自动把JobDataMap上相应的key赋值进来
     * 注意：触发器上的JobDataMap会覆盖掉JobDetail上的JobDataMap上的数据
     */
    @Setter
    private String message;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Date fireTime = context.getFireTime(); //当前执行时间
        Date nextFireTime = context.getNextFireTime(); //下一次执行时间
        long jobRunTime = context.getJobRunTime(); //执行时间点
        JobDetail jobDetail = context.getJobDetail(); //获取jobDetail实例

        // 获取jobDetail实例上的JobDataMap
        // 注意：触发器上的JobDataMap, 它的数据会覆盖掉JobDetail上的JobDataMap
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        int times = jobDataMap.getInt("times");
        jobDataMap.replace("times",++times);

        System.out.println("任务开始执行："+ LocalDateTime.now().format(formatter)+", times="+times+", message="+message);
    }
}
