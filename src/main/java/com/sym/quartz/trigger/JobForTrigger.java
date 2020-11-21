package com.sym.quartz.trigger;

import org.quartz.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by 沈燕明 on 2019/11/9 19:43.
 */
public class JobForTrigger implements Job {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Trigger trigger = context.getTrigger(); //获取触发器
        JobKey jobKey = trigger.getJobKey(); //获取当前执行的job信息
        System.out.println("当前执行的job信息：name="+jobKey.getName()+",group="+jobKey.getGroup()+",执行时间："+formatter.format(LocalDateTime.now()));
    }
}
