package com.sym.serialization.entity.extend;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 如果父类不实现{@link Serializable}接口, 而子类实现, 这样再序列化子类的时候
 * 父类的属性数据就会全部置为默认值
 * @author shenyanming
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SonEntity extends FatherEntity implements Serializable {
    private static final long serialVersionUID = -2333604540713404648L;
    private char sex;
    private double weight;

    public SonEntity(char sex, double weight, int num, String name) {
        this.sex = sex;
        this.weight = weight;
        super.num = num;
        super.name = name;
    }

    @Override
    public String toString() {
        return "Son{" +
                "sex=" + sex +
                ", weight=" + weight +
                ", num=" + num +
                ", name=" + name +
                '}';
    }
}
