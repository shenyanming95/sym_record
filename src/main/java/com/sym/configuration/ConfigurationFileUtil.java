package com.sym.configuration;

import com.sym.configuration.strategy.IRead;
import com.sym.configuration.strategy.impl.ApachePropImpl;
import com.sym.configuration.strategy.impl.JdkPropImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置文件读取工具类
 *
 * @author shenyanming
 * Create on 2021/07/09 10:01
 */
@Slf4j
public class ConfigurationFileUtil {

    private static Map<String/*path*/, IRead> readImplMap = new ConcurrentHashMap<>();
    private static ThreadLocal<Strategy> strategyThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> pathThreadLocal = new ThreadLocal<>();

    public static void setStrategy(Strategy strategy) {
        strategyThreadLocal.set(Objects.requireNonNull(strategy));
    }

    public static void setPath(String path) {
        pathThreadLocal.set(path);
    }

    public static String getValue(String key) {
        String path = pathThreadLocal.get();
        if (Objects.isNull(path)) {
            throw new RuntimeException("you should set the path first");
        }
        return getValue(path, key);
    }

    public static String getValue(String path, String key) {
        return getReadImpl(path).getValue(key);
    }

    private static IRead getReadImpl(String path) {
        Strategy strategy = getStrategy();
        log.info("use strategy - {}", strategy.getName());
        return readImplMap.computeIfAbsent(getPathKey(path), p -> {
            switch (strategy) {
                case JDK:
                    return new JdkPropImpl(path);
                case APACHE:
                    return new ApachePropImpl(path);
                default:
                    throw new IllegalArgumentException("not support strategy, " + strategy.getName());
            }
        });
    }

    private static String getPathKey(String path) {
        switch (getStrategy()) {
            case JDK:
                return path + ":1";
            case APACHE:
                return path + ":2";
            default:
                return path;
        }
    }

    private static Strategy getStrategy() {
        Strategy strategy = strategyThreadLocal.get();
        if (Objects.isNull(strategy)) {
            strategy = Strategy.JDK;
        }
        return strategy;
    }

    public enum Strategy {
        JDK(JdkPropImpl.class.getName()),
        APACHE(ApachePropImpl.class.getName());

        private String name;

        Strategy(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
