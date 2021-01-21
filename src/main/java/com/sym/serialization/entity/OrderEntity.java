package com.sym.serialization.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 实现 #{@link Serializable}接口的实体类, 用于 JDK 的序列化
 *
 * @author shenyanming
 * @date 2019/12/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = 8629447987496982363L;

    private Long orderId;
    private String orderName;
    private OrderStatus orderStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    /**
     * 属性使用 transient 关键字, 则序列化和反序列化时都不会处理它, 默认都为null
     */
    private transient BigDecimal cost;

    public enum OrderStatus {
        // 示例
        ACTIVE,
        INACTIVE,
        TIMEOUT,
        FINISH
    }
}
