package com.sym.guava.eventbus.subscriber;

import com.google.common.eventbus.Subscribe;
import com.sym.guava.eventbus.event.CommodityEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 通过在方法上加上{@link Subscribe}注解, 并且定义方法参数为某一事件,
 * 当调用{@link com.google.common.eventbus.EventBus#post(Object)}方法时
 * 如果发布的事件类型为当前方法参数的事件类型或子类事件类型, 方法就会被回调.
 */
@Slf4j
public class CommoditySubscriber {

    @Subscribe
    public void handle(CommodityEvent event){
        log.info("来商品了: {}", event);
        throw new RuntimeException("Subscriber处理事件发生异常");
    }
}
