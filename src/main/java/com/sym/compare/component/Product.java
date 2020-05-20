package com.sym.compare.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Comparator;

/**
 * 若一个类未实现{@link Comparable}接口, 那它本身就不支持排序.
 * 需要借助外部接口, 即{@link java.util.Comparator}来实现
 * 排序
 *
 * @author shenyanming
 * @date 2020/5/10 8:51.
 */
@AllArgsConstructor
@ToString
@Data
public class Product {

    /**
     * 表示产品id
     */
    private int id;

    /**
     * 由于{@link Product}未实现{@link Comparable}, 所以它不能自己实现排序,
     * 需要借助于外部接口{@link Comparator}
     *
     * @return Product比较器
     */
    public static Comparator<Product> getComparator() {
        return new Comparator<Product>() {

            /**
             * 用于比较的方法
             * @param o1 第一个Product对象
             * @param o2 第二个Product对象
             * @return 有三种返回值：负值、0、正值. 返回负值表示o1小于o2; 返回值0表示o1等于o2; 返回正值, 表示o1大于o2
             */
            @Override
            public int compare(Product o1, Product o2) {
                return o1.id - o2.id;
            }
        };
    }
}
