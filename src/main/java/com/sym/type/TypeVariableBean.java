package com.sym.type;

import com.sym.type.bean.TypeBean;
import lombok.Data;

import java.util.List;

/**
 * 表示类型变量{@link java.lang.reflect.TypeVariable}的实体类
 *
 * @author shenyanming
 * Create on 2021/07/09 11:32
 */
@Data
class TypeVariableBean<K extends TypeBean, V> {
    /**
     * 属于 TypeVariable
     */
    // K 的上边界指：SubClassEntity
    K key;
    // V 没有指定, 则它的上边界指：Object
    V value;

    /**
     * 不属于 TypeVariable
     */
    V[] values;
    String string;
    List<K> kList;
}
