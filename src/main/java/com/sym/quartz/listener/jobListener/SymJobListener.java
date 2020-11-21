package com.sym.quartz.listener.jobListener;

import org.quartz.*;

/**
 * 实现{@link JobListener}就可以在job执行期间, 监听它的事件
 *
 * Created by 沈燕明 on 2019/11/9 20:06.
 */
public class SymJobListener implements JobListener {

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
        System.out.println("Job即将执行,"+getInfo(context));
    }


    /**
     * job执行前, 如果{@link TriggerListener}否决此Job执行, 则调用此方法
     * 它与{@link SymJobListener#jobExecutionVetoed}方法是互斥的, 只有一个会执行
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("job被否决执行,"+this.getInfo(context));
    }

    /**
     * job执行后
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println("job执行后,"+this.getInfo(context));
        System.out.println();
        // 如果job执行期间发生了异常，则异常在jobException
    }

    private String getInfo(JobExecutionContext context){
        JobDetail jobDetail = context.getJobDetail();
        JobKey jobKey = jobDetail.getKey();
        return "Job信息：name="+jobKey.getName()+",group="+jobKey.getGroup();
    }
}
