package com.sym.guava.eventbus.subscriber;

import com.google.common.eventbus.Subscribe;
import com.sym.guava.eventbus.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 通过在方法上加上{@link Subscribe}注解, 并且定义方法参数为某一事件,
 * 当调用{@link com.google.common.eventbus.EventBus#post(Object)}方法时
 * 如果发布的事件类型为当前方法参数的事件类型或子类事件类型, 方法就会被回调.
 *
 * @author ym.shen
 * Created on 2020/4/21 14:34
 */
@Slf4j
public class OrderSubscriber {

    /**
     * 加了注解{@link Subscribe}的方法只能有一个参数
     *
     * @param orderEvent
     */
    @Subscribe
    public void handler(OrderEvent orderEvent) {
        log.info("来订单了: {}", orderEvent);
    }

    @Subscribe
    public void multi(OrderEvent orderEvent) {
        log.info("多余仅用于测试的方法");
    }
}
