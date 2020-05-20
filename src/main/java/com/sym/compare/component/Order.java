package com.sym.compare.component;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * 一个类实现了{@link Comparable}接口, 意味着它支持排序.
 * 泛型{@link Order}表示另一个比较对象, 当然都是同一种类型
 * 才有的比较.
 *
 * @author shenyanming
 * @date 2020/5/10 8:41.
 */
@AllArgsConstructor
@ToString
public class Order implements Comparable<Order> {

    /**
     * 表示订单id
     */
    private int id;


    /**
     * 比较方法
     *
     * @param otherOrder 表示另一笔订单
     * @return 有三种返回值：负值、0、正值. 负值表示当前订单小于另一笔订单,
     * 0表示两笔订单相等, 正值表示当前订单大于另一笔订单.
     */
    @Override
    public int compareTo(Order otherOrder) {
        // 若返回“负数”, 意味着“x比y小”;
        // 返回“零”, 意味着“x等于y”;
        // 返回“正数”, 意味着“x大于y”
        return id - otherOrder.id;
    }

}
