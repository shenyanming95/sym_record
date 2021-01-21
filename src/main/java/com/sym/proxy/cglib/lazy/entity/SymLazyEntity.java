package com.sym.proxy.cglib.lazy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 用于懒加载的实体类
 * <p>
 * Created by shenym on 2020/1/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class SymLazyEntity {
    private Integer id;
    private String name;
    private BigDecimal salary;
    private Double rate;
}
