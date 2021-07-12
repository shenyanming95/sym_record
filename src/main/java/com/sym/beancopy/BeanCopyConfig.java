package com.sym.beancopy;

import org.dozer.DozerBeanMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * bean copy 工具配置类
 *
 * @author shenyanming
 * Create on 2021/07/07 14:31
 */
public class BeanCopyConfig {

    private static Map<Strategy, IBeanCopy> strategiesMap = new HashMap<>(8);

    public static void setStrategy(Strategy strategy) {
        getOrNewInstance(strategy);
    }

    public IBeanCopy getImpl() {
        return getImpl(Strategy.DOZER);
    }

    public IBeanCopy getImpl(Strategy strategy) {
        return getOrNewInstance(strategy);
    }

    private static IBeanCopy getOrNewInstance(Strategy strategy) {
        return strategiesMap.computeIfAbsent(strategy, k -> selectStrategy(strategy));
    }

    /**
     * 选择不同的策略实现
     *
     * @param strategy 策略
     * @return 实现类
     */
    private static IBeanCopy selectStrategy(Strategy strategy) {
        switch (Objects.requireNonNull(strategy)) {
            case DOZER:
                return new Dozer();
            case MODEL_MAPPER:
                return new ModelMapper();
            default:
                throw new IllegalArgumentException("strategy[" + strategy + "] not support");
        }
    }

    /* class */

    /**
     * 对象拷贝的抽象接口
     *
     * @author shenyanming
     * Create on 2021/07/07 14:38
     */
    public interface IBeanCopy {

        /**
         * 将原对象映射成目标对象
         *
         * @param source     原对象
         * @param targetType 目标对象类型
         * @return 目标对象实体
         */
        <T> T map(Object source, Class<T> targetType);

        /**
         * 对象属性拷贝
         *
         * @param source 原对象
         * @param target 目标对象
         */
        void copy(Object source, Object target);

        /**
         * 策略说明
         */
        String description();
    }

    private static class Dozer implements IBeanCopy {

        private DozerBeanMapper dozer = new DozerBeanMapper();

        @Override
        public <T> T map(Object source, Class<T> targetType) {
            return dozer.map(source, targetType);
        }

        @Override
        public void copy(Object source, Object target) {
            dozer.map(source, target);
        }

        @Override
        public String description() {
            return dozer.getClass().getName();
        }
    }

    private static class ModelMapper implements IBeanCopy {

        private org.modelmapper.ModelMapper modelMapper =
                new org.modelmapper.ModelMapper();

        @Override
        public <T> T map(Object source, Class<T> targetType) {
            return modelMapper.map(source, targetType);
        }

        @Override
        public void copy(Object source, Object target) {
            modelMapper.map(source, target);
        }

        @Override
        public String description() {
            return modelMapper.getClass().getName();
        }
    }

    public enum Strategy {
        DOZER, MODEL_MAPPER
    }


}
