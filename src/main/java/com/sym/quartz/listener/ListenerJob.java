package com.sym.quartz.listener;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by 沈燕明 on 2019/11/9 20:16.
 */
public class ListenerJob implements Job {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("job["+context.getJobDetail().getKey()+"]执行, "+formatter.format(LocalDateTime.now()));
    }
}
