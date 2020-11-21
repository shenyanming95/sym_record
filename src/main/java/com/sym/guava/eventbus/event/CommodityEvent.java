package com.sym.guava.eventbus.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品相关的事件, 通过{@link com.google.common.eventbus.EventBus#post(Object)}
 * 方法就可以发布此事件, 它会自动回调加了{@link com.google.common.eventbus.Subscribe}
 * 并且方法参数类型为当前事件和其子类事件的方法
 *
 * @author ym.shen
 * Created on 2020/4/21 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class CommodityEvent implements Serializable {

    private static final long serialVersionUID = -7766626536121435422L;

    private long commodityId;
    private String info;

    public CommodityEvent(){
        this(10086, "商品信息");
    }
}
