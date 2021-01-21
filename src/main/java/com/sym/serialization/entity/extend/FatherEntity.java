package com.sym.serialization.entity.extend;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 如果父类不实现{@link Serializable}接口, 而子类实现, 这样再序列化子类的时候
 * 父类的属性数据就会全部置为默认值
 * @author shenyanming
 */
@Data
@ToString
public class FatherEntity {
    protected int num;
    protected String name;
}
