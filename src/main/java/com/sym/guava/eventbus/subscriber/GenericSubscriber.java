package com.sym.guava.eventbus.subscriber;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ym.shen
 * Created on 2020/4/21 14:37
 */
@Slf4j
public class GenericSubscriber {

    /**
     * 如果方法参数被定义为{@link Object}, 则表示它会处理任何事件
     *
     * @param event 任意事件
     */
    @Subscribe
    public void handle(Object event) {
        log.info("通用的订阅者, 事件类型: {}", event.getClass().getName());
    }
}
