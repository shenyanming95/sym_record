package com.sym.quartz.listener.jobListener;

import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * Created by 沈燕明 on 2019/11/9 20:39.
 */
public class SymTriggerListener implements TriggerListener {

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
        System.out.println("触发器["+trigger.getKey()+"]执行了..");
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
        System.out.println("触发器["+trigger.getKey()+"]执行完..");
    }
}
