package com.sym.guava.eventbus.handler;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * 订阅者处理事件发生异常了就会调用该类
 *
 * @author ym.shen
 * Created on 2020/4/21 14:03
 */
@Slf4j
public class DefaultSubscriberExceptionHandler implements SubscriberExceptionHandler {

    @Override
    public void handleException(Throwable exception, SubscriberExceptionContext context) {
        Object event = context.getEvent();
        EventBus eventBus = context.getEventBus();
        Object subscriber = context.getSubscriber();
        Method subscriberMethod = context.getSubscriberMethod();
        log.error("消息处理异常, 事件: {}, 消息中心: {}, 订阅对象: {}, 订阅方法: {}, 异常内容: {}",
                event, eventBus, subscriber, subscriberMethod.getName(), exception);
    }
}
