package com.sym;

import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author shenyanming
 * Create on 2021/07/12 17:20
 */
public class OtherTest {

    @Test
    public void test01() {
        int[] arrays = {1, 2, 3, 4};
        Integer[] integers = convert(arrays);
        System.out.println(Arrays.toString(integers));
        int[] ints = convert(integers);
        System.out.println(Arrays.toString(ints));
    }

    @Test
    public void test02() {
        Handler.on(() -> Runtime.getRuntime().availableProcessors())
                .completely(System.out::println)
                .exceptionally(Throwable::printStackTrace);
    }

    /**
     * int[] 转 Integer[]
     */
    public static Integer[] convert(int[] array) {
        if (Objects.isNull(array)) return null;
        return Arrays.stream(array).boxed().toArray(Integer[]::new);
    }

    /**
     * Integer[] 转 int[]
     */
    public static int[] convert(Integer[] array) {
        if (Objects.isNull(array)) return null;
        return Arrays.stream(array).mapToInt(Integer::intValue).toArray();
    }

    public static class Handler {

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
}
