package com.sym.quickstart;

import com.github.rholder.retry.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author shenyanming
 * Create on 2021/07/07 17:59
 */
@Slf4j
public class RetryerTest {
    private Retryer<Object> retryer;

    @Before
    public void before(){
        retryer = RetryerBuilder.newBuilder()
                .retryIfException()
                //等待策略：每次请求间隔递增1s
                .withWaitStrategy(WaitStrategies.incrementingWait(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS))
                //停止策略 : 尝试请求3次
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        log.info("阶梯活动奖励判断重试, 异常信息:{}, 重试次数:{}", attempt.getExceptionCause(), attempt.getAttemptNumber());
                    }
                }).build();
    }

    @Test
    public void test01(){
        try {
            retryer.call(()->{
                throw new RuntimeException("模拟异常");
            });
        } catch (ExecutionException e) {
            log.info("ExecutionException出错, ", e);
        } catch (RetryException e) {
            log.info("RetryException出错, ", e);
        }
    }
}
