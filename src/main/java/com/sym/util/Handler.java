package com.sym.util;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author shenyanming
 * Created on 2020/11/5 10:38
 */
public class Handler {

    private Throwable throwable;
    private Object result;

    public static <T> Handler on(Supplier<T> supplier) {
        Handler h = new Handler();
        try {
            h.result = supplier.get();
        } catch (Throwable e) {
            h.throwable = e;
        }
        return h;
    }

    public Handler exceptionally(Consumer<Throwable> consumer) {
        if (Objects.nonNull(throwable)) {
            consumer.accept(throwable);
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> Handler completely(Consumer<T> consumer) {
        if (Objects.nonNull(result)) {
            consumer.accept((T) result);
        }
        return this;
    }
}
