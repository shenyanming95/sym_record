package com.sym.beancopy;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author shenyanming
 * Create on 2021/07/07 14:55
 */
@Slf4j
public class BeanCopyUtil {

    private static BeanCopyConfig config = new BeanCopyConfig();
    private static BeanCopyConfig.Strategy lastStrategy = BeanCopyConfig.Strategy.DOZER;
    private static BeanCopyConfig.IBeanCopy delegate = config.getImpl(lastStrategy);

    public static void copy(Object source, Object target) {
        log.info("use strategy[{}]", delegate.description());
        delegate.copy(source, target);
    }

    public static <T> T map(Object source, Class<T> targetType) {
        log.info("use strategy[{}]", delegate.description());
        return delegate.map(source, targetType);
    }

    /**
     * thread unsafe
     *
     * @param newStrategy 新策略
     */
    public static void reset(BeanCopyConfig.Strategy newStrategy) {
        if (Objects.equals(lastStrategy, newStrategy)) {
            return;
        }
        lastStrategy = newStrategy;
        delegate = config.getImpl(newStrategy);
    }
}
