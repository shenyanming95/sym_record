package com.sym.reflection.type.typeVariable;

import com.sym.reflection.domain.SubClassEntity;
import lombok.Data;

import java.util.List;

/**
 * 表示类型变量{@link java.lang.reflect.TypeVariable}的实体类
 * <p>
 * Created by shenym on 2019/12/31.
 */
@Data
class TypeVariableBean<K extends SubClassEntity, V> {
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
