package com.sym.guava.eventbus.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * {@link OrderEvent}的子类事件
 *
 * @author ym.shen
 * Created on 2020/4/21 16:13
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class OrderEvent0 extends OrderEvent {
    private static final long serialVersionUID = -703613124641446809L;

    private String msg = "订单事件子类";
}
