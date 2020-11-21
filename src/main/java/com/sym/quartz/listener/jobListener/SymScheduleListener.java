package com.sym.quartz.listener.jobListener;


import org.quartz.listeners.SchedulerListenerSupport;

/**
 * Created by 沈燕明 on 2019/11/9 20:51.
 */
public class SymScheduleListener extends SchedulerListenerSupport {
    @Override
    public void schedulerStarted() {
        System.out.println("调度容器启动l。。");
    }
}

