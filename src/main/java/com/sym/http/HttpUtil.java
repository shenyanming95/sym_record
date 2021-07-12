package com.sym.http;

import com.sym.http.protocol.HttpRequest;
import com.sym.http.protocol.HttpResponse;
import com.sym.http.strategy.IHttpStrategy;
import com.sym.http.strategy.impl.ApacheHttpClientStrategy;
import com.sym.http.strategy.impl.JdkHttpStrategy;
import com.sym.http.strategy.impl.OkClientStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HTTP Client 工具类
 *
 * @author shenyanming
 * Create on 2021/07/08 10:54
 */
@Slf4j
public class HttpUtil {

    private static ThreadLocal<Strategy> strategyThreadLocal = new ThreadLocal<>();
    private static Map<Strategy, IHttpStrategy> strategyMap = new ConcurrentHashMap<>(8);

    public static void setStrategy(Strategy strategy) {
        Strategy old;
        if (Objects.nonNull((old = strategyThreadLocal.get())) && old == strategy) {
            return;
        }
        strategyThreadLocal.set(strategy);
    }

    public static HttpResponse get(HttpRequest request) {
        return getStrategyImpl().get(request);
    }

    public static HttpResponse post(HttpRequest request) {
        return getStrategyImpl().post(request);
    }

    public static HttpResponse put(HttpRequest request) {
        return getStrategyImpl().put(request);
    }

    public static HttpResponse delete(HttpRequest request) {
        return getStrategyImpl().delete(request);
    }

    private static IHttpStrategy getStrategyImpl() {
        return strategyMap.computeIfAbsent(getStrategy(), s -> {
            switch (s) {
                case JDK:
                    return new JdkHttpStrategy();
                case OK_CLINT:
                    return new OkClientStrategy();
                case APACHE_HTTP_CLIENT:
                    return new ApacheHttpClientStrategy();
                default:
                    throw new IllegalArgumentException("illegal strategy");
            }
        });
    }

    private static Strategy getStrategy() {
        Strategy strategy = strategyThreadLocal.get();
        if (Objects.isNull(strategy)) {
            strategy = Strategy.OK_CLINT;
        }
        log.info("use strategy [{}]", strategy.getName());
        return strategy;
    }


    public enum Strategy {
        JDK(JdkHttpStrategy.class.getName()),
        APACHE_HTTP_CLIENT(ApacheHttpClientStrategy.class.getName()),
        OK_CLINT(OkClientStrategy.class.getName());

        private String name;

        Strategy(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
